package com.raulastete.lazypizza.presentation.ui.model

data class OrderItemCardUi(
    val id: Long,
    val imageUrl: String,
    val name: String,
    val toppings: List<String>?,
    val count: Int,
    val unitPrice: String,
    val totalPrice: String
) {
    val canDecreaseQuantity = 1 <= count
}