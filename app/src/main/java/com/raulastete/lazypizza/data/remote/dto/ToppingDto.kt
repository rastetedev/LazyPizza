package com.raulastete.lazypizza.data.remote.dto

import com.google.firebase.database.PropertyName
import com.raulastete.lazypizza.domain.entity.Topping

data class ToppingDto(
    @get:PropertyName("id") @set:PropertyName("id")
    var id: String = "",
    @get:PropertyName("name") @set:PropertyName("name")
    var name: String = "",
    @get:PropertyName("price") @set:PropertyName("price")
    var price: Double = 0.0,
    @get:PropertyName("imageUrl") @set:PropertyName("imageUrl")
    var imageUrl: String = ""
)

fun ToppingDto.toDomain() =
    Topping(
        id = id,
        name = name,
        unitPrice = price,
        imageUrl = imageUrl
    )