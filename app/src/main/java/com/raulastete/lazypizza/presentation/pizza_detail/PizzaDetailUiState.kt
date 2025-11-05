package com.raulastete.lazypizza.presentation.pizza_detail

import com.raulastete.lazypizza.presentation.ui.model.ToppingCardUi

data class PizzaDetailUiState(
    val isLoading: Boolean = false,
    val toppingCardUis: List<ToppingCardUi> = emptyList(),
)
