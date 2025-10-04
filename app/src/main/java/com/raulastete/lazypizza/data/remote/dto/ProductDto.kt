package com.raulastete.lazypizza.data.remote.dto

import com.google.firebase.database.PropertyName
import com.raulastete.lazypizza.domain.entity.Product

data class ProductDto(
    @get:PropertyName("id") @set:PropertyName("id")
    var id: String = "",
    @get:PropertyName("name") @set:PropertyName("name")
    var name: String = "",
    @get:PropertyName("description") @set:PropertyName("description")
    var description: String = "",
    @get:PropertyName("price") @set:PropertyName("price")
    var price: Double = 0.0,
    @get:PropertyName("category") @set:PropertyName("category")
    var category: String = "",
    @get:PropertyName("imageUrl") @set:PropertyName("imageUrl")
    var imageUrl: String = ""
)

fun ProductDto.toDomain() =
    Product(
        id = id,
        name = name,
        description = description,
        price = price,
        categoryId = category,
        imageUrl = imageUrl
    )