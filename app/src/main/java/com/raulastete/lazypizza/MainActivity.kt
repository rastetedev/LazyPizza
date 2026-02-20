package com.raulastete.lazypizza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.raulastete.lazypizza.ui.component.CountableProductCard
import com.raulastete.lazypizza.ui.component.CountableProductDetails
import com.raulastete.lazypizza.presentation.home.PizzaCard
import com.raulastete.lazypizza.presentation.home.PizzaCardDetails
import com.raulastete.lazypizza.ui.theme.LazyPizzaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            LazyPizzaTheme {

                val count = remember { mutableIntStateOf(0) }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    PizzaCard(
                        details = PizzaCardDetails(
                            image = "",
                            name = "Margherita",
                            ingredients = "Tomato sauce, mozzarella, fresh basil, olive oil",
                            price = "$8.99"
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    CountableProductCard(
                        details = CountableProductDetails(
                            image = "",
                            name = "Margherita",
                            unitPrice = "$8.99",
                            totalPrice = "$17.98",
                            count = count.value
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        onClickAddToCartButton = {
                            count.value = 1
                        },
                        onClickDeleteFromCartButton = {
                            count.value = 0
                        },
                        onClickDecreaseCountButton = {
                            if (count.value > 0) {
                                count.value--
                            }
                        },
                        onClickIncreaseCountButton = {
                            count.value++
                        }
                    )
                }
            }
        }
    }
}