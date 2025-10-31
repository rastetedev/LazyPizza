package com.raulastete.lazypizza.domain.entity

data class Topping(
    val id: String,
    val name: String,
    val imageUrl: String,
    val unitPrice: Double
)