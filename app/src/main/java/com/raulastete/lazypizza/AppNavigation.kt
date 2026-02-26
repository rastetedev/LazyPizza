package com.raulastete.lazypizza

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raulastete.lazypizza.presentation.home.HomeScreen
import com.raulastete.lazypizza.presentation.pizza_detail.PizzaDetailScreen
import kotlinx.serialization.Serializable

@Serializable
sealed interface NavRoute {
    @Serializable
    data object Home : NavRoute
    
    @Serializable
    data class PizzaDetail(val productId: String) : NavRoute
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoute.Home
    ) {
        composable<NavRoute.Home> {
            HomeScreen(
                onNavigateToPizzaDetail = { productId ->
                    navController.navigate(NavRoute.PizzaDetail(productId))
                }
            )
        }
        composable<NavRoute.PizzaDetail> {
            PizzaDetailScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
