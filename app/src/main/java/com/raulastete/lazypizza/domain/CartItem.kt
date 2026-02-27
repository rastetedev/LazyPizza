package com.raulastete.lazypizza.domain

data class CartItem(
    val id: String,
    val product: Product,
    val quantity: Int,
    val toppings: Map<Product, Int>? = null
)