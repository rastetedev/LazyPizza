package com.raulastete.lazypizza.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderItemDto(
    @PrimaryKey val id: String,
    val productId: String,
    val count: Int,
    val userId: String
)