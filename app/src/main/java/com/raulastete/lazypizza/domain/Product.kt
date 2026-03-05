package com.raulastete.lazypizza.domain

import java.math.BigDecimal

data class Product(
    val id: String,
    val imageUrl: String,
    val name: String,
    val description: String?,
    val unitPrice: BigDecimal
)
