package com.raulastete.lazypizza.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.raulastete.lazypizza.data.local.dto.OrderItemDto
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderItemDao {

    @Query("SELECT * FROM order_items WHERE userId = :userId")
    fun getOrderItemsByUser(userId: String): Flow<List<OrderItemDto>>

    @Upsert
    suspend fun upsertOrderItem(orderItemDto: OrderItemDto) : Long

    @Query("DELETE FROM order_items WHERE id = :orderItemId")
    suspend fun deleteOrderItem(orderItemId: Long)

    @Query("DELETE FROM order_items WHERE productId = :productId AND userId = :userId")
    suspend fun deleteOrderItemByProductId(productId: String, userId: String)

    @Query("SELECT * FROM order_items WHERE id = :orderItemId")
    suspend fun getOrderItemById(orderItemId: Long): OrderItemDto?

    @Query("SELECT * FROM order_items WHERE productId = :productId AND userId = :userId")
    suspend fun getOrderItemsByProductId(productId: String, userId: String): List<OrderItemDto>
}