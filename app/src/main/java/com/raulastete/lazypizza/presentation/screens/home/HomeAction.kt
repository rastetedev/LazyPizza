package com.raulastete.lazypizza.presentation.screens.home

sealed interface HomeAction {
    object OnClickLogoutButton : HomeAction
    data class OnQueryProducts(val query: String) : HomeAction
    data class OnClickCategoryChip(val category: String) : HomeAction
    data class OnClickPizzaCard(val pizzaId: String) : HomeAction
    data class OnAddToCartComplementFoodCard(val complementFoodId: String) : HomeAction
    data class OnIncrementQuantityComplementFood(val complementFoodId: String) : HomeAction
    data class OnDecreaseQuantityComplementFood(val complementFoodId: String) : HomeAction
    data class OnRemoveFromCartComplementFood(val complementFoodId: String) : HomeAction
}

