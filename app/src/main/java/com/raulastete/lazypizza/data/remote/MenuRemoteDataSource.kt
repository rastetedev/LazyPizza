package com.raulastete.lazypizza.data.remote

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.raulastete.lazypizza.data.remote.dto.CategoryDto
import com.raulastete.lazypizza.data.remote.dto.ProductDto
import com.raulastete.lazypizza.data.remote.dto.ToppingDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class MenuRemoteDataSource(
    database: FirebaseDatabase
) {

    private val categoriesRef = database.getReference("categories")
    private val productsRef = database.getReference("products")
    private val toppingsRef = database.getReference("toppings")

    fun getAllToppings(): Flow<List<ToppingDto>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val toppings = snapshot.children.mapNotNull { toppingSnapshot ->
                    val topping = toppingSnapshot.getValue(ToppingDto::class.java)
                    topping?.id = toppingSnapshot.key ?: ""
                    topping
                }
                trySend(toppings.sortedBy { it.id })
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        toppingsRef.addValueEventListener(listener)

        awaitClose {
            toppingsRef.removeEventListener(listener)
        }
    }

    fun getAllProducts(): Flow<List<ProductDto>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val products = snapshot.children.mapNotNull { productSnapshot ->
                    val product = productSnapshot.getValue(ProductDto::class.java)
                    product?.id = productSnapshot.key ?: ""
                    product
                }
                trySend(products.sortedBy { it.id })
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        productsRef.addValueEventListener(listener)

        awaitClose {
            productsRef.removeEventListener(listener)
        }
    }

    fun getAllCategories(): Flow<List<CategoryDto>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val categories = snapshot.children.mapNotNull { categorySnapshot ->
                    val category = categorySnapshot.getValue(CategoryDto::class.java)
                    category?.id = categorySnapshot.key ?: ""
                    category
                }
                trySend(categories.sortedBy { it.displayOrder })
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        categoriesRef.addValueEventListener(listener)

        awaitClose {
            categoriesRef.removeEventListener(listener)
        }
    }

}