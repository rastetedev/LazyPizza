package com.raulastete.lazypizza.presentation.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.raulastete.lazypizza.R
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

    @Serializable
    data object OrderHistory
}

enum class MainDestinations(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
) {
    MENU(R.string.menu_nav_item, R.drawable.menu_nav_icon ),
    CART(R.string.cart_nav_item, R.drawable.cart_nav_icon),
    ORDER_HISTORY(R.string.order_history_nav_item, R.drawable.order_history_nav_icon),
}