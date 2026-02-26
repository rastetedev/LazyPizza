package com.raulastete.lazypizza.presentation.model

sealed interface ProductUi {
    val id: String
    val type: ProductType
}

enum class ProductType {
    PIZZA, COMPLEMENT
}