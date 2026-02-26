package com.raulastete.lazypizza.presentation.pizza_detail

sealed interface PizzaDetailAction {
    data object OnClickBackButton : PizzaDetailAction
    data object OnClickAddToCart : PizzaDetailAction
    data class OnIncrementTopping(val toppingId: String) : PizzaDetailAction
    data class OnDecreaseTopping(val toppingId: String) : PizzaDetailAction
}
