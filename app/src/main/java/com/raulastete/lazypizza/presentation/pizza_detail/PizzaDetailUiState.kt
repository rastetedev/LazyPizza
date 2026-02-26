package com.raulastete.lazypizza.presentation.pizza_detail

data class PizzaDetailUiState(
    val pizzaName: String = "",
    val pizzaDescription: String = "",
    val pizzaImage: String = "",
    val toppings: List<ToppingUi> = emptyList(),
    val totalPrice: String = "$0.00",
    val isLoading: Boolean = true
)
