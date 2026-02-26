package com.raulastete.lazypizza.presentation.model

data class PizzaUi(
    override val id: String,
    override val type: ProductType = ProductType.PIZZA,
    val image: String,
    val name: String,
    val ingredients: String,
    val unitPrice: String
) : ProductUi