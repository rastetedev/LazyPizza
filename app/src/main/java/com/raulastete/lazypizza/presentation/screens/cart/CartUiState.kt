package com.raulastete.lazypizza.presentation.screens.cart

import com.raulastete.lazypizza.presentation.model.CartItemUi
import com.raulastete.lazypizza.presentation.model.RecommendedProductUi

data class CartUiState(
    val cartItems: List<CartItemUi> = emptyList(),
    val recommendedProducts: List<RecommendedProductUi> = emptyList(),
    val totalPrice: String = "$0.00",
    val isLoading: Boolean = true
)
