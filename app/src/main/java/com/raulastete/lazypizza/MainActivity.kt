package com.raulastete.lazypizza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raulastete.lazypizza.presentation.home.HomeScreen
import com.raulastete.lazypizza.presentation.navigation.Routes
import com.raulastete.lazypizza.presentation.pizza_detail.PizzaDetailScreen
import com.raulastete.lazypizza.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.Home) {
                    composable<Routes.Home> {
                        HomeScreen(
                            navigateToPizzaDetail = { pizzaId ->
                                navController.navigate(Routes.PizzaDetail(pizzaId))
                            }
                        )
                    }

                    composable<Routes.PizzaDetail> {
                        PizzaDetailScreen(
                            navigateBack = {
                                navController.navigateUp()
                            }
                        )
                    }
                }
            }
        }
    }
}