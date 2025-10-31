package com.raulastete.lazypizza.data.local.dto

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "order_item_toppings",
    foreignKeys = [
        ForeignKey(
            entity = OrderItemDto::class,
            parentColumns = ["id"],
            childColumns = ["orderItemId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("orderItemId")]
)
data class OrderItemToppingDto(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val toppingId: String,
    val orderItemId: Long,
    val toppingName: String,
    val count: Int,
    val price: Double
)