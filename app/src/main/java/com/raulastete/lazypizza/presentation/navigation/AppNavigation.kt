package com.raulastete.lazypizza.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raulastete.lazypizza.presentation.screens.cart.CartScreen
import com.raulastete.lazypizza.presentation.screens.home.HomeScreen
import com.raulastete.lazypizza.presentation.screens.pizza_detail.PizzaDetailScreen


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
                },
                onNavigateToCart = { navController.navigate(NavRoute.Cart) }
            )
        }
        composable<NavRoute.PizzaDetail> {
            PizzaDetailScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable<NavRoute.Cart> {
            CartScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
