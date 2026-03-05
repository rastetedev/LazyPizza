package com.raulastete.lazypizza.data.repository

import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.raulastete.lazypizza.data.dto.CategoryDto
import com.raulastete.lazypizza.data.dto.ProductDto
import com.raulastete.lazypizza.data.dto.ToppingDto
import com.raulastete.lazypizza.domain.Category
import com.raulastete.lazypizza.domain.Product
import com.raulastete.lazypizza.domain.ProductRepository
import com.raulastete.lazypizza.domain.Topping
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.tasks.await

class DefaultProductRepository : ProductRepository {

    private val database = Firebase.database

    private val categoryReference = database.getReference("categories")
    private val productsReference = database.getReference("products")
    private val toppingsReference = database.getReference("toppings")

    override fun getProductsByCategory(): Flow<List<Category>> {
        val productsFlow = callbackFlow {
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val products = snapshot.children.mapNotNull { productSnapshot ->
                        val product = productSnapshot.getValue(ProductDto::class.java)
                        productSnapshot.key?.let { productId ->
                            product?.copy(key = productId)
                        }
                    }
                    trySend(products).isSuccess
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            }
            productsReference.addValueEventListener(listener)
            awaitClose { productsReference.removeEventListener(listener) }
        }

        val categoriesFlow = callbackFlow {
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val categories = snapshot.children.mapNotNull { categorySnapshot ->
                        val category = categorySnapshot.getValue(CategoryDto::class.java)
                        categorySnapshot.key?.let { categoryId ->
                            category?.copy(key = categoryId)
                        }
                    }
                    trySend(categories.map {
                        Category(
                            id = it.key,
                            name = it.name,
                            displayOrder = it.displayOrder.toShort(),
                            products = emptyList()
                        )
                    }).isSuccess
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            }
            categoryReference.addValueEventListener(listener)
            awaitClose { categoryReference.removeEventListener(listener) }
        }

        return categoriesFlow.combine(productsFlow) { categories, products ->
            categories.map { category ->
                val categoryProducts = products
                    .filter { it.category == category.id }
                    .map { productDto ->
                        Product(
                            id = productDto.key,
                            imageUrl = productDto.imageUrl,
                            name = productDto.name,
                            description = productDto.description,
                            unitPrice = productDto.price.toBigDecimal(),
                        )
                    }
                category.copy(products = categoryProducts)
            }.sortedBy { it.displayOrder }
        }
    }

    override suspend fun getPizzaById(productId: String): Product? {
        val snapshot = productsReference.child(productId).get().await()
        val productDto = snapshot.getValue(ProductDto::class.java) ?: return null

        return Product(
            id = productId,
            imageUrl = productDto.imageUrl,
            name = productDto.name,
            description = productDto.description,
            unitPrice = productDto.price.toBigDecimal()
        )
    }

    override fun getToppings(): Flow<List<Topping>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val toppings = snapshot.children.mapNotNull { toppingSnapshot ->
                    val topping = toppingSnapshot.getValue(ToppingDto::class.java)
                    toppingSnapshot.key?.let { key ->
                        Topping(
                            id = key,
                            name = topping?.name ?: "",
                            imageUrl = topping?.imageUrl ?: "",
                            unitPrice = topping?.price?.toBigDecimal() ?: 0.0.toBigDecimal()
                        )
                    }
                }
                trySend(toppings).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        toppingsReference.addValueEventListener(listener)
        awaitClose { toppingsReference.removeEventListener(listener) }
    }
}
