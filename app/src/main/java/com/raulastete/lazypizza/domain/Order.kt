package com.raulastete.lazypizza.domain

import java.math.BigDecimal

data class Order(
    val id: String = "",
    val items: List<CartItem>,
    val totalPrice: BigDecimal,
    val timestamp: Long = System.currentTimeMillis()
)
