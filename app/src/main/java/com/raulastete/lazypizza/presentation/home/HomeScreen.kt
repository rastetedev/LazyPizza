package com.raulastete.lazypizza.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.presentation.home.components.CategoryListRow
import com.raulastete.lazypizza.presentation.home.components.CountableProductCard
import com.raulastete.lazypizza.presentation.home.components.PizzaCard
import com.raulastete.lazypizza.ui.components.LPSearchBar
import com.raulastete.lazypizza.ui.components.LPTopbar
import com.raulastete.lazypizza.ui.theme.AppTheme

enum class ProductContentType {
    PIZZA_ITEM,
    GENERIC_ITEM
}

@Composable
fun HomeScreen() {
    HomeScreenContent()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreenContent() {
    val productMap = mapOf<String, List<String>>(
        "PIZZA" to listOf(
            "Margherita",
            "Pepperoni",
            "Hawaiian",
            "Supreme",
            "BBQ Chicken",
            "Veggie Delight"
        ),
        "DRINKS" to listOf(
            "Cola",
            "Lemonade",
            "Iced Tea",
            "Orange Juice",
            "Mineral Water",
            "Craft Beer"
        ),
        "SAUCES" to listOf(
            "Garlic Dip",
            "Spicy Marinara",
            "Ranch Dressing",
            "Blue Cheese Dip",
            "Pesto Sauce",
            "Hot Honey"
        ),
        "DESSERTS" to listOf(
            "Chocolate Lava Cake",
            "Tiramisu",
            "Cheesecake",
            "Brownie with Ice Cream",
            "Panna Cotta",
            "Cinnamon Sticks"
        ),
        "SALADS" to listOf(
            "Caesar Salad",
            "Greek Salad",
            "Caprese Salad",
            "Garden Salad",
            "Cobb Salad",
            "Spinach and Strawberry Salad"
        ),
        "APPETIZERS" to listOf(
            "Garlic Bread",
            "Mozzarella Sticks",
            "Chicken Wings",
            "Bruschetta",
            "Onion Rings",
            "Stuffed Mushrooms"
        )
    )

    Scaffold(
        containerColor = AppTheme.colorScheme.surface
    ) { contentPadding ->
        Column(
            Modifier
                .padding(contentPadding)
                .padding(horizontal = 16.dp)
        ) {
            LPTopbar(modifier = Modifier.fillMaxWidth())

            Spacer(Modifier.height(8.dp))

            LPSearchBar(
                modifier = Modifier.fillMaxWidth(),
                query = "",
                onQueryChange = {}
            )
            Spacer(Modifier.height(8.dp))
            CategoryListRow(
                categories = listOf("Pizza", "Drinks", "Sauces", "Ice Cream")
            ) {

            }

            LazyColumn(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                productMap.forEach { (category, productList) ->
                    stickyHeader(key = category) {
                        CategoryHeader(category)
                    }

                    items(
                        items = productList,
                        key = { product -> "${category}_${product}" },
                        contentType = {
                            if (category == "PIZZA") {
                                ProductContentType.PIZZA_ITEM
                            } else {
                                ProductContentType.GENERIC_ITEM
                            }
                        }
                    ) {
                        if (category == "PIZZA") {
                            PizzaCard(image = "", modifier = Modifier.fillMaxWidth()) { }
                        } else {
                            CountableProductCard(
                                modifier = Modifier.fillMaxWidth(),
                                name = it,
                                price = "$1.00",
                                totalPrice = "$0.00",
                                count = 0,
                                onClickAddToCart = {},
                                onClickDecreaseCount = {},
                                onClickIncreaseCount = {},
                                onClickRemoveFromCart = {}
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun CategoryHeader(name: String) {
    Text(
        text = name,
        style = AppTheme.typography.label2Semibold,
        color = AppTheme.colorScheme.textSecondary,
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colorScheme.surface)
            .padding(vertical = 12.dp)

    )
}

@Composable
private fun CategorySection(category: String, products: List<String>) {

}

@Preview(showBackground = true)
@Composable
private fun HomeScreenContentPreview() {
    AppTheme {
        HomeScreenContent()
    }
}