package com.raulastete.lazypizza.presentation.screens.pizza_detail

sealed interface PizzaDetailAction {
    object OnClickBackButton : PizzaDetailAction
    object OnClickAddToCart : PizzaDetailAction
    data class OnIncrementTopping(val toppingId: String) : PizzaDetailAction
    data class OnDecreaseTopping(val toppingId: String) : PizzaDetailAction
}
