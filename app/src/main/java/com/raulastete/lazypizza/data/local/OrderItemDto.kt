package com.raulastete.lazypizza.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderItemDto(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val productId: String,
    val count: Int,
    val userId: String
)