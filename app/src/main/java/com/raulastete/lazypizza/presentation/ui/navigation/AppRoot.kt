package com.raulastete.lazypizza.presentation.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.raulastete.lazypizza.presentation.cart.CartScreen
import com.raulastete.lazypizza.presentation.menu.MenuScreen
import com.raulastete.lazypizza.presentation.order_history.OrderHistoryScreen
import com.raulastete.lazypizza.presentation.ui.components.LPBadge
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@Composable
fun AppRoot() {
    var currentDestination by rememberSaveable { mutableStateOf(MainDestinations.MENU) }

    val myNavigationSuiteItemColors = NavigationSuiteDefaults.itemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = AppTheme.colorScheme.primary,
            selectedTextColor = AppTheme.colorScheme.textPrimary,
            indicatorColor = AppTheme.colorScheme.primary8,
            unselectedIconColor = AppTheme.colorScheme.textSecondary,
            unselectedTextColor = AppTheme.colorScheme.textSecondary
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            selectedIconColor = AppTheme.colorScheme.primary,
            selectedTextColor = AppTheme.colorScheme.textPrimary,
            indicatorColor = AppTheme.colorScheme.primary8,
            unselectedIconColor = AppTheme.colorScheme.textSecondary,
            unselectedTextColor = AppTheme.colorScheme.textSecondary
        )
    )

    NavigationSuiteScaffold(
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContainerColor = AppTheme.colorScheme.surfaceHigher,
            navigationRailContainerColor = AppTheme.colorScheme.surfaceHigher,
        ),
        navigationSuiteItems = {
            MainDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(it.icon),
                            contentDescription = stringResource(it.title)
                        )
                    },
                    label = { Text(stringResource(it.title)) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it },
                    colors = myNavigationSuiteItemColors,
                    badge = {
                        if (it == MainDestinations.CART) {
                            LPBadge(count = "3")
                        }
                    }
                )
            }
        }
    ) {
        when (currentDestination) {
            MainDestinations.MENU -> MenuScreen(
                navigateToPizzaDetail = {}
            )

            MainDestinations.CART -> CartScreen(
                navigateToMenu = {}
            )

            MainDestinations.ORDER_HISTORY -> OrderHistoryScreen(
                navigateToLogin = {}
            )
        }
    }
}