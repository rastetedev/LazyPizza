package com.raulastete.lazypizza.data.dto

data class ProductDto(
    val key: String = "",
    val imageUrl: String = "",
    val name: String = "",
    val description: String? = null,
    val price: Float = 0.0f,
    val category: String? = null
)