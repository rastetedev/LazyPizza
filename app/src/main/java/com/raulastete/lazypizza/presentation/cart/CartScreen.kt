package com.raulastete.lazypizza.presentation.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.domain.entity.Product
import com.raulastete.lazypizza.presentation.ui.DeviceMode
import com.raulastete.lazypizza.presentation.ui.components.LPCenterTopbar
import com.raulastete.lazypizza.presentation.ui.components.LPPrimaryButton
import com.raulastete.lazypizza.presentation.ui.components.MessageFullScreen
import com.raulastete.lazypizza.presentation.ui.components.product.OrderItemCard
import com.raulastete.lazypizza.presentation.ui.components.product.RecommendedProductCard
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme
import com.raulastete.lazypizza.presentation.ui.theme.LocalDeviceMode
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
        onAddRecommendedProductToCart = cartViewModel::addRecommendedProductToCart,
        onIncreaseOrderItemCount = cartViewModel::increaseOrderItemCount,
        onDecreaseOrderItemCount = cartViewModel::decreaseOrderItemCount,
        onRemoveOrderItemFromCartClick = cartViewModel::removeOrderItemFromCart
    )
}

@Composable
private fun CartScreenContent(
    uiState: CartUiState,
    navigateToMenu: () -> Unit,
    onAddRecommendedProductToCart: (Product) -> Unit,
    onIncreaseOrderItemCount: (Long, Int) -> Unit,
    onDecreaseOrderItemCount: (Long, Int) -> Unit,
    onRemoveOrderItemFromCartClick: (Long) -> Unit
) {
    val deviceMode = LocalDeviceMode.current

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
                when (deviceMode) {
                    DeviceMode.PhonePortrait -> SingleColumnCartList(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        uiState = uiState,
                        onAddRecommendedProductToCart = onAddRecommendedProductToCart,
                        onIncreaseOrderItemCount = onIncreaseOrderItemCount,
                        onDecreaseOrderItemCount = onDecreaseOrderItemCount,
                        onRemoveOrderItemFromCartClick = onRemoveOrderItemFromCartClick
                    )

                    else -> TwoColumnCartList(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        uiState = uiState,
                        onAddRecommendedProductToCart = onAddRecommendedProductToCart,
                        onIncreaseOrderItemCount = onIncreaseOrderItemCount,
                        onDecreaseOrderItemCount = onDecreaseOrderItemCount,
                        onRemoveOrderItemFromCartClick = onRemoveOrderItemFromCartClick
                    )
                }
            }
        }
    }
}

@Composable
private fun SingleColumnCartList(
    modifier: Modifier = Modifier,
    uiState: CartUiState,
    onAddRecommendedProductToCart: (Product) -> Unit,
    onIncreaseOrderItemCount: (Long, Int) -> Unit,
    onDecreaseOrderItemCount: (Long, Int) -> Unit,
    onRemoveOrderItemFromCartClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(uiState.orderItems, key = { it.id }) { orderItem ->
            OrderItemCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                orderItemCardUi = orderItem,
                onClickIncreaseCount = { onIncreaseOrderItemCount(orderItem.id, orderItem.count) },
                onClickDecreaseCount = { onDecreaseOrderItemCount(orderItem.id, orderItem.count) },
                onClickRemoveFromCart = { onRemoveOrderItemFromCartClick(orderItem.id) }
            )
        }

        if (uiState.recommendedItems.isNotEmpty()) {
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
                        items(uiState.recommendedItems, key = { it.id }) { product ->
                            RecommendedProductCard(
                                product = product,
                                onAddToCartClick = {
                                    onAddRecommendedProductToCart(product)
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
                    text = stringResource(R.string.proceed_to_checkout_button, uiState.totalPrice)
                ) { }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun TwoColumnCartList(
    modifier: Modifier = Modifier,
    uiState: CartUiState,
    onAddRecommendedProductToCart: (Product) -> Unit,
    onIncreaseOrderItemCount: (Long, Int) -> Unit,
    onDecreaseOrderItemCount: (Long, Int) -> Unit,
    onRemoveOrderItemFromCartClick: (Long) -> Unit
) {
    Row(modifier) {
        LazyColumn(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(uiState.orderItems, key = { it.id }) { orderItem ->
                OrderItemCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    orderItemCardUi = orderItem,
                    onClickIncreaseCount = {
                        onIncreaseOrderItemCount(
                            orderItem.id,
                            orderItem.count
                        )
                    },
                    onClickDecreaseCount = {
                        onDecreaseOrderItemCount(
                            orderItem.id,
                            orderItem.count
                        )
                    },
                    onClickRemoveFromCart = { onRemoveOrderItemFromCartClick(orderItem.id) }
                )
            }
            item {
                Spacer(Modifier.height(12.dp))
            }
        }

        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
                .background(
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        bottomStart = 16.dp
                    ),
                    color = AppTheme.colorScheme.surfaceHigher
                )
                .padding(vertical = 16.dp)
        ) {
            if (uiState.recommendedItems.isNotEmpty()) {
                Column {
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
                        item { Spacer(Modifier.width(8.dp)) }
                        items(uiState.recommendedItems, key = { it.id }) { product ->
                            RecommendedProductCard(
                                product = product,
                                onAddToCartClick = {
                                    onAddRecommendedProductToCart(product)
                                }
                            )
                        }
                        item { Spacer(Modifier.width(8.dp)) }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            LPPrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = stringResource(
                    R.string.proceed_to_checkout_button,
                    uiState.totalPrice
                )
            ) { }
        }
    }
}