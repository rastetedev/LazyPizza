package com.raulastete.lazypizza.presentation.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.presentation.ui.components.LPCenterTopbar
import com.raulastete.lazypizza.presentation.ui.components.LPPrimaryButton
import com.raulastete.lazypizza.presentation.ui.components.MessageFullScreen
import com.raulastete.lazypizza.presentation.ui.components.product.OrderItemCard
import com.raulastete.lazypizza.presentation.ui.components.product.RecommendedProductCard
import com.raulastete.lazypizza.presentation.ui.model.OrderItemCardUi
import com.raulastete.lazypizza.presentation.ui.model.RecommendedProductCardUi
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartScreen(
    cartViewModel: CartViewModel = koinViewModel(),
    navigateToMenu: () -> Unit
) {
    val uiState by cartViewModel.cartUiState.collectAsStateWithLifecycle()

    CartScreenContent(
        uiState = uiState,
        navigateToMenu = navigateToMenu,
        onAddProductToCartClick = cartViewModel::addProductToCart,
        onIncreaseOrderItemCount = cartViewModel::increaseOrderItemCount,
        onDecreaseOrderItemCount = cartViewModel::decreaseOrderItemCount,
        onRemoveOrderItemFromCartClick = cartViewModel::removeOrderItemFromCart
    )
}

@Composable
private fun CartScreenContent(
    uiState: CartUiState,
    navigateToMenu: () -> Unit,
    onAddProductToCartClick: (String) -> Unit,
    onIncreaseOrderItemCount: (Long) -> Unit,
    onDecreaseOrderItemCount: (Long) -> Unit,
    onRemoveOrderItemFromCartClick: (Long) -> Unit
) {
    Scaffold(
        topBar = {
            LPCenterTopbar(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.cart_screen)
            )
        },
        containerColor = AppTheme.colorScheme.background
    ) { paddingValues ->

        when {
            uiState.orderItems.isEmpty() -> {
                MessageFullScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    title = stringResource(R.string.cart_empty_title),
                    subtitle = stringResource(R.string.cart_empty_subtitle),
                    buttonText = stringResource(R.string.cart_empty_button),
                    onClick = navigateToMenu
                )
            }

            else -> {
                CartList(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    orderItems = uiState.orderItems,
                    recommendedItems = uiState.recommendedItems,
                    onAddProductToCartClick = onAddProductToCartClick,
                    onIncreaseCountClick = onIncreaseOrderItemCount,
                    onDecreaseCountClick = onDecreaseOrderItemCount,
                    onRemoveOrderItemFromCartClick = onRemoveOrderItemFromCartClick
                )
            }
        }
    }
}

@Composable
private fun CartList(
    modifier: Modifier = Modifier,
    orderItems: List<OrderItemCardUi>,
    recommendedItems: List<RecommendedProductCardUi>,
    onAddProductToCartClick: (String) -> Unit,
    onIncreaseCountClick: (Long) -> Unit,
    onDecreaseCountClick: (Long) -> Unit,
    onRemoveOrderItemFromCartClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(orderItems, key = { it.id }) { orderItem ->
            OrderItemCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                orderItemCardUi = orderItem,
                onClickIncreaseCount = { onIncreaseCountClick(orderItem.id) },
                onClickDecreaseCount = { onDecreaseCountClick(orderItem.id) },
                onClickRemoveFromCart = { onRemoveOrderItemFromCartClick(orderItem.id) }
            )
        }

        if (recommendedItems.isNotEmpty()) {
            item(key = "recommended_section") {
                Column {
                    Spacer(Modifier.height(12.dp))

                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = stringResource(R.string.recommended_section_title),
                        style = AppTheme.typography.label2Semibold.copy(color = AppTheme.colorScheme.textSecondary)
                    )

                    Spacer(Modifier.height(8.dp))

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item { Spacer(Modifier.width(16.dp)) }
                        items(recommendedItems, key = { it.id }) { recommendedItem ->
                            RecommendedProductCard(
                                recommendedProductCardUi = recommendedItem,
                                onAddToCartClick = {
                                    onAddProductToCartClick(recommendedItem.id)
                                }
                            )
                        }
                        item { Spacer(Modifier.width(16.dp)) }
                    }
                }
            }
        }

        item(key = "checkout_button") {
            Column {
                Spacer(Modifier.height(12.dp))
                LPPrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = stringResource(R.string.proceed_to_checkout_button)
                ) { }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true, name = "Empty List")
@Composable
private fun EmptyCartScreenContentPreview() {
    AppTheme {
        CartScreenContent(
            uiState = CartUiState(),
            navigateToMenu = {},
            onAddProductToCartClick = {},
            onRemoveOrderItemFromCartClick = {},
            onIncreaseOrderItemCount = {},
            onDecreaseOrderItemCount = {}
        )
    }
}

@Preview(showBackground = true, name = "WithContent")
@Composable
private fun CartScreenContentPreview() {
    AppTheme {
        CartScreenContent(
            uiState = CartUiState(
                orderItems = listOf(
                    OrderItemCardUi(
                        id = 0L,
                        imageUrl = "",
                        totalPrice = "$10.00",
                        name = "Product 1",
                        unitPrice = "$10.00",
                        count = 1,
                        toppings = null
                    )
                )
            ),
            navigateToMenu = {},
            onAddProductToCartClick = {},
            onRemoveOrderItemFromCartClick = {},
            onIncreaseOrderItemCount = {},
            onDecreaseOrderItemCount = {}
        )
    }
}