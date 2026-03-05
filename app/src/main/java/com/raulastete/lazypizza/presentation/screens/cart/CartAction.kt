package com.raulastete.lazypizza.presentation.screens.cart

sealed interface CartAction {
    data object OnClickBackButton : CartAction
    data object OnClickProceedToCheckout : CartAction
    data class OnAddToCartRecommended(val productId: String) : CartAction
    data class OnIncrementQuantity(val cartItemId: String) : CartAction
    data class OnDecreaseQuantity(val cartItemId: String) : CartAction
    data class OnRemoveFromCart(val cartItemId: String) : CartAction
}
