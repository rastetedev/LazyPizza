package com.raulastete.lazypizza.presentation.model

data class ComplementFoodUi(
    override val id: String,
    override val type: ProductType = ProductType.COMPLEMENT,
    val image: String,
    val name: String,
    val unitPrice: String,
    val totalPrice: String,
    val count: Int = 0,
    val extras: List<String>? = null
) : ProductUi