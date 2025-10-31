package com.raulastete.lazypizza.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderItemDao {

    @Query("DELETE FROM orders WHERE id = :orderItemId")
    suspend fun deleteItem(orderItemId: Long)

    @Query("SELECT * FROM orders WHERE userId = :userId")
    fun getOrderItemsByUser(userId: String): Flow<List<OrderItemDto>>

    @Query("SELECT * FROM orders WHERE productId = :productId")
    suspend fun getOrderItemByProductId(productId: String): OrderItemDto?

    @Query("SELECT * FROM orders WHERE id = :orderItemId")
    suspend fun getOrderItemById(orderItemId: Long): OrderItemDto?

    @Upsert
    suspend fun upsert(orderItemDto: OrderItemDto)

}