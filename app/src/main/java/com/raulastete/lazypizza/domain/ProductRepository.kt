package com.raulastete.lazypizza.domain

import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProductsByCategory(): Flow<List<Category>>

    suspend fun getPizzaById(productId: String): Product?

    fun getToppings(): Flow<List<Topping>>
}
