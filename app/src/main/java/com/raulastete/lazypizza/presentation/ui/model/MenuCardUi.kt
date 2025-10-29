package com.raulastete.lazypizza.presentation.ui.model

sealed interface MenuCardUi {
    val id: String
    val name: String
}

data class PizzaCardUi(
    override val id: String,
    override val name: String,
    val imageUrl: String,
    val description: String,
    val unitPrice: String
) : MenuCardUi

data class GenericProductCardUi(
    override val id: String,
    override val name: String,
    val imageUrl: String,
    val unitPrice: String,
    val totalPrice: String,
    val count: Int = 0
) : MenuCardUi {
    val isNotSelected = count == 0
}