package com.raulastete.lazypizza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.raulastete.lazypizza.presentation.home.components.CountableProductCard
import com.raulastete.lazypizza.presentation.product_detail.components.ToppingCard
import com.raulastete.lazypizza.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            AppTheme {

                var count by remember { mutableStateOf(0) }
                var toppingCount1 by remember { mutableStateOf(0) }
                var toppingCount2 by remember { mutableStateOf(0) }
                var toppingCount3 by remember { mutableStateOf(0) }

                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
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

                    Spacer(Modifier.height(10.dp))

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 0.dp),
                    ) {
                        ToppingCard(
                            modifier = Modifier.weight(1f),
                            productName = "Extra Cheese",
                            price = "$1",
                            count = toppingCount1,
                            onClick = {
                                toppingCount1 = 1
                            },
                            onClickIncreaseCount = {
                                toppingCount1 += 1
                            },
                            onClickDecreaseCount = {
                                toppingCount1 -= 1
                            }
                        )
                        Spacer(Modifier.width(8.dp))
                        ToppingCard(
                            modifier = Modifier.weight(1f),
                            productName = "Extra Cheese",
                            price = "$1",
                            count = toppingCount2,
                            onClick = {
                                toppingCount2 = 1
                            },
                            onClickIncreaseCount = {
                                toppingCount2 += 1
                            },
                            onClickDecreaseCount = {
                                toppingCount2 -= 1
                            }
                        )
                        Spacer(Modifier.width(8.dp))
                        ToppingCard(
                            modifier = Modifier.weight(1f),
                            productName = "Extra Cheese",
                            price = "$1",
                            count = toppingCount3,
                            onClick = {
                                toppingCount3 = 1
                            },
                            onClickIncreaseCount = {
                                toppingCount3 += 1
                            },
                            onClickDecreaseCount = {
                                toppingCount3 -= 1
                            }
                        )
                    }
                }
            }
        }
    }
}