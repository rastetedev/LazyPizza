package com.raulastete.lazypizza.presentation.ui.model

data class ToppingCardUi(
    val id: String,
    val imageUrl: String,
    val name: String,
    val unitPrice: String,
    val count: Int
) {
    val canIncreaseQuantity: Boolean = count <= 3
    val isSelected: Boolean = count > 0
}