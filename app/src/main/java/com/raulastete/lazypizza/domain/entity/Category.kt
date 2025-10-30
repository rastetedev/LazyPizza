package com.raulastete.lazypizza.domain.entity

data class Category(
    val id: String,
    val name: String
) {
    val isPizza = id == PIZZA_CATEGORY_ID
    companion object {
        const val PIZZA_CATEGORY_ID = "PIZZA"
        const val DRINKS_CATEGORY_ID = "DRINKS"
        const val SAUCES_CATEGORY_ID = "SAUCES"
    }
}