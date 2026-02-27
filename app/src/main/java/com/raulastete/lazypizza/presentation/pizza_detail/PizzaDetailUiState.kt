package com.raulastete.lazypizza.presentation.pizza_detail

import com.raulastete.lazypizza.presentation.model.ToppingUi

data class PizzaDetailUiState(
    val pizzaName: String = "",
    val ingredientsText: String = "",
    val pizzaImage: String = "",
    val toppings: List<ToppingUi> = emptyList(),
    val totalPrice: String = "$0.00",
    val isLoading: Boolean = true
)
