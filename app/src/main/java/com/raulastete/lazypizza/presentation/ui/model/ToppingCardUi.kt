package com.raulastete.lazypizza.presentation.ui.model

import com.raulastete.lazypizza.domain.entity.Topping

data class ToppingCardUi(
    val topping: Topping,
    val count: Int
) {
    val canIncreaseQuantity: Boolean = count < 3
    val isSelected: Boolean = count > 0
}