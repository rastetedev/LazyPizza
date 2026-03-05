package com.raulastete.lazypizza.presentation.screens.pizza_detail

import com.raulastete.lazypizza.presentation.model.ToppingUi

data class PizzaDetailUiState(
    val pizzaImage: String = "",
    val pizzaName: String = "",
    val ingredientsText: String = "",
    val toppings: List<ToppingUi> = emptyList(),
    val totalPrice: String = "$0.00",
    val isLoading: Boolean = true
)
