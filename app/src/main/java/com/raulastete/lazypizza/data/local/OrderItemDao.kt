package com.raulastete.lazypizza.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderItemDao {

    @Upsert
    suspend fun upsert(orderItemDto: OrderItemDto)

    @Query("DELETE FROM orders WHERE id = :orderItemId")
    suspend fun deleteItem(orderItemId: String)

    @Query("SELECT * FROM orders WHERE userId = :userId")
    fun getOrderItemsByUser(userId: String): Flow<List<OrderItemDto>>

}