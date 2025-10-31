package com.raulastete.lazypizza.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_items")
data class OrderItemDto(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val productId: String,
    val productCategoryId: String,
    val userId: String,
    val productName: String,
    val productImage: String,
    val unitPrice: Double,
    val count: Int
)