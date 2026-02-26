package com.raulastete.lazypizza.presentation.pizza_detail

data class ToppingUi(
    val id: String,
    val name: String,
    val image: String,
    val unitPrice: String,
    val count: Int = 0
)
