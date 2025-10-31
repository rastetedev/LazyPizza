package com.raulastete.lazypizza.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.raulastete.lazypizza.data.local.dto.OrderItemToppingDto
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderItemToppingDao {

    @Query("SELECT * FROM order_item_toppings WHERE orderItemId = :orderItemId")
    fun getToppingsByOrderItem(orderItemId: Long): Flow<List<OrderItemToppingDto>>

    @Upsert
    fun upsert(orderItemToppings: List<OrderItemToppingDto>)
}