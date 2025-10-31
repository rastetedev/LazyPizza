package com.raulastete.lazypizza.domain.entity

import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
data class OrderItem(
    val id: Long,
    val product: Product,
    val toppings: Map<Topping, Int>? = null,
    val count: Int
)