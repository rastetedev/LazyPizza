package com.raulastete.lazypizza.data.repository

import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.raulastete.lazypizza.data.room.dao.CartDao
import com.raulastete.lazypizza.data.room.table.CartItemEntity
import com.raulastete.lazypizza.data.room.table.toDomain
import com.raulastete.lazypizza.data.room.table.toEntity
import com.raulastete.lazypizza.domain.CartItem
import com.raulastete.lazypizza.domain.CartRepository
import com.raulastete.lazypizza.domain.Category
import com.raulastete.lazypizza.domain.Order
import com.raulastete.lazypizza.domain.Product
import com.raulastete.lazypizza.domain.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.UUID

class DefaultCartRepository(
    private val cartDao: CartDao,
    private val productRepository: ProductRepository
) : CartRepository {

    private val database = Firebase.database
    private val ordersReference = database.getReference("orders")

    override fun getCartItems(): Flow<List<CartItem>> {
        return combine(
            cartDao.getAllItems(),
            productRepository.getToppings()
        ) { entities, allToppings ->
            entities.map { entity ->
                entity.toDomain()
            }
        }
    }

    override suspend fun addOrUpdateItem(item: CartItem) {
        cartDao.insertItem(item.toEntity())
    }

    override suspend fun deleteItem(itemId: String) {
        cartDao.deleteItem(itemId)
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }

    override suspend fun placeOrder(items: List<CartItem>): Result<Unit> {
        return Result.success(Unit)
    }
}