package com.raulastete.lazypizza.domain

const val PIZZA_CATEGORY_ID = "PIZZA"

data class Category(
    val id: String,
    val name: String,
    val displayOrder: Short,
    val products: List<Product>
)