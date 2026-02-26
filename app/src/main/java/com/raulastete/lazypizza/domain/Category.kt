package com.raulastete.lazypizza.domain

data class Category(
    val id: String,
    val name: String,
    val displayOrder: Short
) {
    companion object {
        val PIZZA_CATEGORY_DEFAULT = Category(
            id = "pizza",
            name = "Pizza",
            displayOrder = 1
        )
    }
}