package com.raulastete.lazypizza.presentation.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.domain.entity.Product
import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Menu : Route

    @Serializable
    data object Cart : Route

    @Serializable
    data object OrderHistory : Route
}

sealed interface MenuRoute {
    @Serializable
    data object Home : MenuRoute

    @Serializable
    data class Pizza(val pizza: Product) : MenuRoute
}

data class LazyPizzaTopLevelDestination(
    val route: Route,
    @StringRes val title: Int,
    @DrawableRes val icon: Int
)

class LazyPizzaNavigationActions(private val navController: NavHostController) {

    fun navigateTo(destination: LazyPizzaTopLevelDestination) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

val TOP_LEVEL_DESTINATIONS = listOf(
    LazyPizzaTopLevelDestination(
        route = Route.Menu,
        title = R.string.menu_nav_item,
        icon = R.drawable.menu_nav_icon
    ),
    LazyPizzaTopLevelDestination(
        route = Route.Cart,
        title = R.string.cart_nav_item,
        icon = R.drawable.cart_nav_icon
    ),
    LazyPizzaTopLevelDestination(
        route = Route.OrderHistory,
        title = R.string.order_history_nav_item,
        icon = R.drawable.order_history_nav_icon
    )
)