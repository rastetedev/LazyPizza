package com.raulastete.lazypizza.domain.repository

import com.raulastete.lazypizza.domain.entity.OrderItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun getOrderItemsByUser(userId: String): Flow<List<OrderItem>>

    suspend fun upsertOrderItem(orderItem: OrderItem)

    suspend fun deleteOrderItem(orderItem: OrderItem)
}