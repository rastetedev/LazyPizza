package com.raulastete.lazypizza.domain

import java.math.BigDecimal

data class Topping(
    val id: String,
    val name: String,
    val imageUrl: String,
    val unitPrice: BigDecimal
)