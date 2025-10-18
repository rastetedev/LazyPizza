package com.raulastete.lazypizza.presentation.menu.model

sealed interface ProductUi {
    val id: String
    val imageUrl: String

    val name: String
    val price: Double
}

data class PizzaUi(
    override val id: String,
    override val imageUrl: String,
    override val name: String,
    override val price: Double,
    val description: String,
) : ProductUi

data class CountableProductUi(
    override val id: String,
    override val imageUrl: String,
    override val name: String,
    override val price: Double,
    val count: Int
) : ProductUi {
    val isNotSelected = count < 1
    val totalPrice: String = String.format("%.2f", price * count)
}