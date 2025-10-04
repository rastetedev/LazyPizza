package com.raulastete.lazypizza.data.remote.dto

import com.google.firebase.database.PropertyName
import com.raulastete.lazypizza.domain.entity.Category

data class CategoryDto(
    @get:PropertyName("id") @set:PropertyName("id")
    var id: String = "",
    @get:PropertyName("name") @set:PropertyName("name")
    var name: String = "",
    @get:PropertyName("displayOrder") @set:PropertyName("displayOrder")
    var displayOrder: Int = 0,
    @get:PropertyName("products") @set:PropertyName("products")
    var products: Map<String, Boolean> = emptyMap()
)

fun CategoryDto.toDomain() =
    Category(
        id = id,
        name = name
    )