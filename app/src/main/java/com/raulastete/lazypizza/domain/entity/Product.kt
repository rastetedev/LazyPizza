package com.raulastete.lazypizza.domain.entity

import com.raulastete.lazypizza.domain.entity.Category.Companion.DRINKS_CATEGORY_ID
import com.raulastete.lazypizza.domain.entity.Category.Companion.SAUCES_CATEGORY_ID

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val unitPrice: Double,
    val categoryId: String,
    val extras: List<String>? = null
) {
    val belongsToSaucesCategory: Boolean = categoryId == SAUCES_CATEGORY_ID
    val belongsToDrinksCategory: Boolean = categoryId == DRINKS_CATEGORY_ID
}