package com.raulastete.lazypizza.data.repository

import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.raulastete.lazypizza.data.room.dao.CartDao
import com.raulastete.lazypizza.data.room.table.toDomain
import com.raulastete.lazypizza.data.room.table.toEntity
import com.raulastete.lazypizza.domain.CartItem
import com.raulastete.lazypizza.domain.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultCartRepository(
    private val cartDao: CartDao
) : CartRepository {

    private val database = Firebase.database
    private val ordersReference = database.getReference("orders")

    override fun getCartItems(): Flow<List<CartItem>> {
        return cartDao.getAllItems().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun addOrUpdateItem(
        cartItem: CartItem
    ) {
        cartDao.insertItem(cartItem.toEntity())
    }

    override suspend fun deleteItemBasedOnCartItem(itemId: String) {
        cartDao.deleteItem(itemId)
    }

    override suspend fun deleteItemBasedOnProduct(productId: String) {
        cartDao.deleteItemFromProduct(productId)
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }

    override suspend fun placeOrder(items: List<CartItem>): Result<Unit> {
        return Result.success(Unit)
    }
}