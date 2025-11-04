package com.raulastete.lazypizza.presentation.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldLayout
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.raulastete.lazypizza.presentation.ui.DeviceMode
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme
import com.raulastete.lazypizza.presentation.ui.theme.LocalDeviceMode

@Composable
fun LazyPizzaNavigationWrapper(
    currentDestination: NavDestination?,
    navigateToTopLevelDestination: (LazyPizzaTopLevelDestination) -> Unit,
    content: @Composable () -> Unit,
) {

    val deviceMode = LocalDeviceMode.current

    val navLayoutType = when (deviceMode) {
        DeviceMode.PhonePortrait -> NavigationSuiteType.NavigationBar
        DeviceMode.PhoneLandscape -> NavigationSuiteType.NavigationRail
        DeviceMode.TabletPortrait -> NavigationSuiteType.NavigationRail
        DeviceMode.TabletLandscape -> NavigationSuiteType.NavigationDrawer
    }

    NavigationSuiteScaffoldLayout(
        layoutType = navLayoutType,
        navigationSuite = {
            when (navLayoutType) {
                NavigationSuiteType.NavigationBar -> LazyPizzaBottomNavigationBar(
                    currentDestination = currentDestination,
                    navigateToTopLevelDestination = navigateToTopLevelDestination,
                )

                NavigationSuiteType.NavigationRail -> LazyPizzaNavigationRail(
                    currentDestination = currentDestination,
                    navigateToTopLevelDestination = navigateToTopLevelDestination
                )

                NavigationSuiteType.NavigationDrawer -> PermanentNavigationDrawerContent(
                    currentDestination = currentDestination,
                    navigateToTopLevelDestination = navigateToTopLevelDestination,
                )
            }
        }
    ) {
        content()
    }
}

@Composable
fun LazyPizzaBottomNavigationBar(
    currentDestination: NavDestination?,
    navigateToTopLevelDestination: (LazyPizzaTopLevelDestination) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 12.dp, shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
        containerColor = AppTheme.colorScheme.surfaceHigher,
    ) {
        TOP_LEVEL_DESTINATIONS.forEach { topLevelDestination ->
            NavigationBarItem(
                selected = currentDestination.hasRoute(topLevelDestination),
                onClick = { navigateToTopLevelDestination(topLevelDestination) },
                icon = {
                    Icon(
                        painter = painterResource(id = topLevelDestination.icon),
                        contentDescription = stringResource(id = topLevelDestination.title),
                    )
                },
                label = {
                    Text(
                        stringResource(topLevelDestination.title),
                        style = AppTheme.typography.title4
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = AppTheme.colorScheme.primary8,
                    unselectedIconColor = AppTheme.colorScheme.textSecondary,
                    selectedIconColor = AppTheme.colorScheme.primary,
                    selectedTextColor = AppTheme.colorScheme.textPrimary,
                    unselectedTextColor = AppTheme.colorScheme.textSecondary
                )
            )
        }
    }
}

@Composable
fun LazyPizzaNavigationRail(
    currentDestination: NavDestination?,
    navigateToTopLevelDestination: (LazyPizzaTopLevelDestination) -> Unit
) {
    NavigationRail(
        modifier = Modifier.fillMaxHeight(),
        containerColor = AppTheme.colorScheme.background,
    ) {
        Row {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Spacer(Modifier.weight(1f))
                TOP_LEVEL_DESTINATIONS.forEach { topLevelDestination ->
                    NavigationRailItem(
                        selected = currentDestination.hasRoute(topLevelDestination),
                        onClick = { navigateToTopLevelDestination(topLevelDestination) },
                        icon = {
                            Icon(
                                painter = painterResource(id = topLevelDestination.icon),
                                contentDescription = stringResource(
                                    id = topLevelDestination.title,
                                ),
                            )
                        },
                        label = {
                            Text(
                                stringResource(topLevelDestination.title),
                                style = AppTheme.typography.title4
                            )
                        },
                        colors = NavigationRailItemDefaults.colors(
                            indicatorColor = AppTheme.colorScheme.primary8,
                            unselectedIconColor = AppTheme.colorScheme.textSecondary,
                            selectedIconColor = AppTheme.colorScheme.primary,
                            selectedTextColor = AppTheme.colorScheme.textPrimary,
                            unselectedTextColor = AppTheme.colorScheme.textSecondary
                        )
                    )
                }
                Spacer(Modifier.weight(1f))
            }
            VerticalDivider()
        }
    }
}

@Composable
fun PermanentNavigationDrawerContent(
    currentDestination: NavDestination?,
    navigateToTopLevelDestination: (LazyPizzaTopLevelDestination) -> Unit,
) {
    PermanentDrawerSheet(
        modifier = Modifier.sizeIn(minWidth = 200.dp, maxWidth = 300.dp),
        drawerContainerColor = AppTheme.colorScheme.background,
    ) {
        Spacer(Modifier.weight(1f))
        Column(Modifier.padding(horizontal = 16.dp)) {
            TOP_LEVEL_DESTINATIONS.forEach { topLevelDestination ->
                NavigationDrawerItem(
                    selected = currentDestination.hasRoute(topLevelDestination),
                    label = {
                        Text(
                            text = stringResource(id = topLevelDestination.title),
                            modifier = Modifier.padding(horizontal = 16.dp),
                            style = AppTheme.typography.title4
                        )
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = topLevelDestination.icon),
                            contentDescription = null,
                        )
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.Transparent,
                        selectedContainerColor = AppTheme.colorScheme.primary8,
                        selectedIconColor = AppTheme.colorScheme.primary,
                        unselectedIconColor = AppTheme.colorScheme.textSecondary,
                        selectedTextColor = AppTheme.colorScheme.textPrimary,
                        unselectedTextColor = AppTheme.colorScheme.textSecondary
                    ),
                    onClick = { navigateToTopLevelDestination(topLevelDestination) },
                )
            }
        }
        Spacer(Modifier.weight(1f))
    }
}

fun NavDestination?.hasRoute(destination: LazyPizzaTopLevelDestination): Boolean =
    this?.hasRoute(destination.route::class) ?: false