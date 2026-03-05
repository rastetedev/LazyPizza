package com.raulastete.lazypizza.presentation.screens.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raulastete.lazypizza.presentation.model.CartItemUi
import com.raulastete.lazypizza.presentation.model.RecommendedProductUi
import com.raulastete.lazypizza.presentation.component.PrimaryButton
import com.raulastete.lazypizza.presentation.theme.LazyPizzaTheme
import com.raulastete.lazypizza.presentation.theme.body1Medium
import com.raulastete.lazypizza.presentation.theme.label2Semibold
import com.raulastete.lazypizza.presentation.util.bottomFadingEdge
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartScreen(
    viewModel: CartViewModel = koinViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CartScreenContent(
        uiState = uiState,
        onAction = { action ->
            if (action is CartAction.OnClickBackButton) {
                onBack()
            } else {
                viewModel.onAction(action)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CartScreenContent(
    uiState: CartUiState,
    onAction: (CartAction) -> Unit
) {
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Cart",
                        style = MaterialTheme.typography.body1Medium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .bottomFadingEdge(
                        state = listState,
                        height = 100.dp,
                        color = MaterialTheme.colorScheme.background
                    ),
                contentPadding = PaddingValues(bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(uiState.cartItems, key = { it.id }) { item ->
                    CartCard(
                        cartItem = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .animateItem(),
                        onClickAddToCartButton = { },
                        onClickDeleteFromCartButton = {
                            onAction(CartAction.OnRemoveFromCart(item.id))
                        },
                        onClickDecreaseCountButton = {
                            onAction(CartAction.OnDecreaseQuantity(item.id))
                        },
                        onClickIncreaseCountButton = {
                            onAction(CartAction.OnIncrementQuantity(item.id))
                        }
                    )
                }

                item(key = "recommended_section") {
                    RecommendedSection(
                        recommendedProducts = uiState.recommendedProducts,
                        onAction = onAction
                    )
                }
            }

            CheckoutButton(
                totalPrice = uiState.totalPrice,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                onAction = onAction
            )
        }
    }
}

@Composable
private fun RecommendedSection(
    recommendedProducts: List<RecommendedProductUi>,
    onAction: (CartAction) -> Unit
) {
    Column {
        Text(
            text = "RECOMMENDED TO ADD TO YOUR ORDER",
            style = MaterialTheme.typography.label2Semibold.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = recommendedProducts,
                key = { it.id }
            ) { product ->
                RecommendedCard(
                    modifier = Modifier.animateItem(),
                    recommendedProduct = product,
                    onAddButtonClick = { onAction(CartAction.OnAddToCartRecommended(product.id)) }
                )
            }
        }
    }
}

@Composable
private fun CheckoutButton(
    totalPrice: String,
    modifier: Modifier,
    onAction: (CartAction) -> Unit
) {
    PrimaryButton(
        text = "Proceed to Checkout ($totalPrice)",
        onClick = { onAction(CartAction.OnClickProceedToCheckout) },
        modifier = modifier
    )
}

@Composable
@Preview
private fun CartScreenPreview() {
    LazyPizzaTheme {
        CartScreenContent(
            uiState = CartUiState(
                totalPrice = "$54.50",
                cartItems = listOf(
                    // 3 Pizzas
                    CartItemUi(
                        id = "1L",
                        productId = "",
                        image = "",
                        name = "Pepperoni Feast",
                        unitPrice = "$12.90",
                        totalPrice = "$12.90",
                        count = 1,
                        extras = listOf(
                            "1 x Tomato sauce",
                            "2 x Mozzarella",
                            "1 x Fresh basil",
                            "1 x Olive oil"
                        )
                    ),
                    CartItemUi(
                        id = "2L",
                        productId = "",
                        image = "",
                        name = "Margherita Classic",
                        unitPrice = "$10.50",
                        totalPrice = "$21.00",
                        count = 2,
                        extras = listOf("1 x Extra Cheese")
                    ),
                    CartItemUi(
                        id = "3L",
                        productId = "",
                        image = "",
                        name = "Hawaiian Paradise",
                        unitPrice = "$11.20",
                        totalPrice = "$11.20",
                        count = 1
                    ),
                    // 2 Complementos
                    CartItemUi(
                        id = "4L",
                        productId = "",
                        image = "",
                        name = "Coca-Cola 500ml",
                        unitPrice = "$2.50",
                        totalPrice = "$5.00",
                        count = 2
                    ),
                    CartItemUi(
                        id = "5L",
                        productId = "",
                        image = "",
                        name = "Chocolate Ice Cream",
                        unitPrice = "$4.90",
                        totalPrice = "$4.90",
                        count = 1
                    )
                ),
                recommendedProducts = listOf(
                    RecommendedProductUi(
                        id = "r1",
                        name = "Garlic Bread",
                        image = "",
                        unitPrice = "$3.50"
                    ),
                    RecommendedProductUi(
                        id = "r2",
                        name = "BBQ Wings",
                        image = "",
                        unitPrice = "$6.50"
                    ),
                    RecommendedProductUi(
                        id = "r3",
                        name = "Fanta Orange",
                        image = "",
                        unitPrice = "$2.50"
                    )
                )
            ),
            onAction = {}
        )
    }
}