package com.raulastete.lazypizza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raulastete.lazypizza.presentation.home.HomeScreen
import com.raulastete.lazypizza.ui.navigation.Routes
import com.raulastete.lazypizza.presentation.pizza_detail.PizzaDetailScreen
import com.raulastete.lazypizza.ui.DeviceModeManager
import com.raulastete.lazypizza.ui.theme.AppTheme
import com.raulastete.lazypizza.ui.theme.LocalDeviceMode

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val windowSizeClass = calculateWindowSizeClass(this)
                val configuration = LocalConfiguration.current

                val deviceModeManager = remember(windowSizeClass, configuration) {
                    DeviceModeManager(
                        windowSizeClass.widthSizeClass,
                        windowSizeClass.heightSizeClass,
                        configuration
                    )
                }

                val currentDeviceMode = deviceModeManager.getDeviceMode()

                CompositionLocalProvider(
                    LocalDeviceMode provides currentDeviceMode,
                ) {
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
                            PizzaDetailScreen {
                                navController.navigateUp()
                            }
                        }
                    }
                }
            }
        }
    }
}
