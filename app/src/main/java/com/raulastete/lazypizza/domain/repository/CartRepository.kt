package com.raulastete.lazypizza.domain.repository

import com.raulastete.lazypizza.domain.entity.OrderItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun getOrderItemsByUser(userId: String): Flow<List<OrderItem>>

    suspend fun increaseProductCountInCart(productId: String)

    suspend fun decreaseProductCountInCart(productId: String)

    suspend fun deleteProductFromCart(productId: String)

    suspend fun deleteOrderItem(orderItem: OrderItem)

}