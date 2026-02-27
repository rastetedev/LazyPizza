package com.raulastete.lazypizza.presentation.cart

sealed interface CartAction {
    data object OnClickBackButton : CartAction
    data object OnClickProceedToCheckout : CartAction
    data class OnAddToCartRecommended(val productId: String) : CartAction
    data class OnIncrementQuantity(val productId: String) : CartAction
    data class OnDecreaseQuantity(val productId: String) : CartAction
    data class OnRemoveFromCart(val productId: String) : CartAction
}
