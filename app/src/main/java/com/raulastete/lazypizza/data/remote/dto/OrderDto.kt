package com.raulastete.lazypizza.data.remote.dto

import com.google.firebase.database.PropertyName
import com.raulastete.lazypizza.domain.entity.OrderItem

data class OrderDto(

    val items: List<OrderItem>,
    val timestamp: Long,
    @get:PropertyName("userId") @set:PropertyName("userId")
    var userId: String
)