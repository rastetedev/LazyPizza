package com.raulastete.lazypizza.presentation.ui.model

sealed class ProductCard(
    open val id: String,
    open val imageUrl: String,
    open val name: String,
    open val price: Double
) {

    data class PizzaCard(
        override val id: String,
        override val imageUrl: String,
        override val name: String,
        override val price: Double,
        val description: String,
    ) : ProductCard(id, imageUrl, name, price)

    data class GenericProductCard(
        override val id: String,
        override val imageUrl: String,
        override val name: String,
        override val price: Double,
        val count: Int = 0,
    ) : ProductCard(id, imageUrl, name, price) {
        val isNotSelected = count == 0
        val totalCost = price * count
        val formattedTotalCost = String.format("%.2f", totalCost)
    }

    data class ToppingCard(
        override val id: String,
        override val imageUrl: String,
        override val name: String,
        override val price: Double,
        val count: Int,
        val maximumQuantity: Int = 3,
    ) : ProductCard(id, imageUrl, name, price) {
        val isSelected = count > 0
        val isNotSelected = count == 0
        val canIncreaseQuantity = count < maximumQuantity
        val totalCost = price * count
        val formattedTotalCost = String.format("%.2f", totalCost)
    }

    data class ProductCartCard(
        override val id: String,
        override val imageUrl: String,
        override val name: String,
        override val price: Double,
        val count: Int,
        val toppings: List<String>?,
        val minimumQuantity: Int = 1,
    ) : ProductCard(id, imageUrl, name, price) {
        val totalCost = price * count
        val formattedTotalCost = String.format("%.2f", totalCost)
    }

    data class RecommendProductCard(
        override val id: String,
        override val imageUrl: String,
        override val name: String,
        override val price: Double,
    ) : ProductCard(id, imageUrl, name, price)

}