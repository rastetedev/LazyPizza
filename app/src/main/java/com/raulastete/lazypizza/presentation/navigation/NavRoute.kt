package com.raulastete.lazypizza.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavRoute {
    @Serializable
    data object Home : NavRoute

    @Serializable
    data class PizzaDetail(val productId: String) : NavRoute

    @Serializable
    data object Cart : NavRoute
}