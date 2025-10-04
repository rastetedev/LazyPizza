package com.raulastete.lazypizza.presentation.home.model

sealed interface ProductUi

data class PizzaUi(
    val imageUrl: String,
    val name: String,
    val description: String,
    val price: Double
) : ProductUi

data class CountableProductUi(
    val imageUrl: String,
    val name: String,
    val price: Double,
    val count: Int
) : ProductUi {
    val isNotSelected = count < 1
    val totalPrice: String = String.format("%.2f", price * count)
}