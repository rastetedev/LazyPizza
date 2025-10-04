package com.raulastete.lazypizza.domain.entity

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Double,
    val categoryId: String
)