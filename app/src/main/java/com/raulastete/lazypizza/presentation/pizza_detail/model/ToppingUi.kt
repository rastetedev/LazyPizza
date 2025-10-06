package com.raulastete.lazypizza.presentation.pizza_detail.model

import com.raulastete.lazypizza.domain.entity.Topping
import kotlin.String

data class ToppingUi(
    val id: String,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val count: Int
) {
    val isNotSelected = count == 0
    val isSelected = count > 0
    val canIncreaseQuantity = count < MAX_QUANTITY_ALLOWED_PER_TOPPING

    companion object {
        const val MAX_QUANTITY_ALLOWED_PER_TOPPING = 3
    }
}

fun Topping.toUi() = ToppingUi(
    id = id,
    name = name,
    imageUrl = imageUrl,
    price = price,
    count = 0
)