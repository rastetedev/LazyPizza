package com.raulastete.lazypizza.domain.entity

import com.raulastete.lazypizza.domain.entity.Category.Companion.PIZZA_CATEGORY_ID

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val unitPrice: Double,
    val categoryId: String
) {
    val belongsToPizzaCategory: Boolean = categoryId == PIZZA_CATEGORY_ID
}