package com.raulastete.lazypizza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.raulastete.lazypizza.presentation.home.components.CountableProductCard
import com.raulastete.lazypizza.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            AppTheme {

                var count by remember { mutableStateOf(0) }

                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CountableProductCard(
                        modifier = Modifier.fillMaxWidth(),
                        name = "Margharita",
                        price = "$1.00",
                        totalPrice = "$2.00",
                        count = count,
                        onClickAddToCart = {
                            count = 1
                        },
                        onClickIncreaseCount = {
                            count += 1
                        },
                        onClickDecreaseCount = {
                            count -= 1
                        },
                        onClickRemoveFromCart = {
                            count = 0
                        }
                    )
                }
            }
        }
    }
}