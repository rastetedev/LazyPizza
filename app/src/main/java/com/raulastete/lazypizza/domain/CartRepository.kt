package com.raulastete.lazypizza.domain

import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(): Flow<List<CartItem>>
    suspend fun addOrUpdateItem(cartItem: CartItem)

    suspend fun deleteItemBasedOnCartItem(itemId: String)
    suspend fun deleteItemBasedOnProduct(productId: String)
    suspend fun clearCart()
    suspend fun placeOrder(items: List<CartItem>): Result<Unit>
}
