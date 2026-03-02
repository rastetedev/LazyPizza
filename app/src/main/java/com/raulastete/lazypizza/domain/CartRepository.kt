package com.raulastete.lazypizza.domain

import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(): Flow<List<CartItem>>
    suspend fun addOrUpdateItem(item: CartItem)
    suspend fun deleteItem(itemId: String)
    suspend fun clearCart()
    suspend fun placeOrder(items: List<CartItem>): Result<Unit>
}
