package com.raulastete.lazypizza.presentation.ui.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    object Home

    @Serializable
    data class PizzaDetail(val pizzaId: String) {
        companion object {
            const val PIZZA_ID_ARG = "pizzaId"
        }
    }

    @Serializable
    data object Cart
}