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
import com.raulastete.lazypizza.presentation.ui.DeviceModeManager
import com.raulastete.lazypizza.presentation.ui.navigation.AppRoot
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme
import com.raulastete.lazypizza.presentation.ui.theme.LocalDeviceMode

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
                    AppRoot()
                }
            }
        }
    }
}
