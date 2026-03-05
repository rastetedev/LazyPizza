package com.raulastete.lazypizza.presentation.model

data class CartItemUi(
    val id: String,
    val productId: String,
    val image: String,
    val name: String,
    val unitPrice: String,
    val totalPrice: String,
    val count: Int,
    val extras: List<String>? = null
)