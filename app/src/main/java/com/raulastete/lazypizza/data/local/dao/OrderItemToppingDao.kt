package com.raulastete.lazypizza.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.raulastete.lazypizza.data.local.dto.OrderItemToppingDto

@Dao
interface OrderItemToppingDao {

    @Query("SELECT * FROM order_item_toppings WHERE orderItemId = :orderItemId")
    suspend fun getToppingsByOrderItem(orderItemId: Long): List<OrderItemToppingDto>

    @Insert
    suspend fun insertToppings(orderItemToppings: List<OrderItemToppingDto>)

    @Query("DELETE FROM order_item_toppings WHERE orderItemId = :orderItemId")
    suspend fun deleteToppings(orderItemId: Long)

}