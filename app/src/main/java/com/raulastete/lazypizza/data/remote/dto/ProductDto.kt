package com.raulastete.lazypizza.data.remote.dto

import com.google.firebase.database.PropertyName

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