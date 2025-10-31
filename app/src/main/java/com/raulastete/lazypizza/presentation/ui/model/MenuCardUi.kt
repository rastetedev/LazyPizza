package com.raulastete.lazypizza.presentation.ui.model

import com.raulastete.lazypizza.domain.entity.Product

sealed interface MenuCardUi {
    val product : Product
}

data class PizzaCardUi(
    override val product: Product
) : MenuCardUi

data class GenericProductCardUi(
    override val product: Product,
    val totalPrice: String,
    val count: Int = 0
) : MenuCardUi