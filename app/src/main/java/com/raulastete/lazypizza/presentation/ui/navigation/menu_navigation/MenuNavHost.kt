package com.raulastete.lazypizza.presentation.ui.navigation.menu_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raulastete.lazypizza.presentation.menu.MenuScreen
import com.raulastete.lazypizza.presentation.pizza_detail.PizzaDetailScreen
import kotlinx.serialization.Serializable

@Serializable
data object MenuDestination

@Serializable
data class PizzaDetailDestination(val pizzaId: String) {
    companion object {
        const val PIZZA_ID_ARG = "pizzaId"
    }
}

@Composable
fun MenuNavHost() {

    val menuNavController = rememberNavController()

    NavHost(navController = menuNavController, startDestination = MenuDestination) {

        composable<MenuDestination> {
            MenuScreen(
                navigateToPizzaDetail = {
                    menuNavController.navigate(PizzaDetailDestination(it))
                }
            )
        }

        composable<PizzaDetailDestination> {
            PizzaDetailScreen(navigateBack = { menuNavController.navigateUp() })
        }
    }
}