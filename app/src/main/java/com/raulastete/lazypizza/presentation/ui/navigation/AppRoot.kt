package com.raulastete.lazypizza.presentation.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.raulastete.lazypizza.domain.entity.Product
import com.raulastete.lazypizza.presentation.cart.CartScreen
import com.raulastete.lazypizza.presentation.menu.MenuScreen
import com.raulastete.lazypizza.presentation.order_history.OrderHistoryScreen
import com.raulastete.lazypizza.presentation.pizza_detail.PizzaDetailScreen
import kotlin.reflect.typeOf

@Composable
fun AppRoot() {

    val navController = rememberNavController()

    val navigationActions = remember(navController) { LazyPizzaNavigationActions(navController) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    LazyPizzaNavigationWrapper(
        currentDestination = currentDestination,
        navigateToTopLevelDestination = navigationActions::navigateTo
    ) {
        LazyPizzaNavHost(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(PaddingValues(100.dp)), //Hack to deal with NavigationSuiteScaffold
            navController = navController
        )
    }
}

@Composable
private fun LazyPizzaNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Route.Menu
    ) {
        composable<Route.Menu> {

            val innerNavController = rememberNavController()

            NavHost(
                navController = innerNavController,
                startDestination = MenuRoute.Home,
                modifier = Modifier.fillMaxSize()
            ) {

                composable<MenuRoute.Home> {
                    MenuScreen(
                        navigateToPizzaDetail = {
                            innerNavController.navigate(MenuRoute.Pizza(it))
                        }
                    )
                }

                composable<MenuRoute.Pizza>(
                    typeMap = mapOf(
                        typeOf<Product>() to CustomNavType.ProductType
                    )
                ) {
                    val pizzaProduct = it.toRoute<MenuRoute.Pizza>().pizza
                    PizzaDetailScreen(
                        pizzaProduct = pizzaProduct,
                        navigateBack = { innerNavController.navigateUp() })
                }
            }
        }

        composable<Route.Cart> {
            CartScreen {

            }
        }

        composable<Route.OrderHistory> {
            OrderHistoryScreen {

            }
        }
    }
}