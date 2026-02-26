package com.raulastete.lazypizza.domain

import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProductsByCategory(): Flow<Map<Category, List<Product>>>

    suspend fun searchProducts(query: String): Flow<List<Product>>

    suspend fun getProductById(productId: String): Product?

}