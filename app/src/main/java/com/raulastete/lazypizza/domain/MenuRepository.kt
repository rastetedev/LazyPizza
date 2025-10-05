package com.raulastete.lazypizza.domain

import com.raulastete.lazypizza.domain.entity.Category
import com.raulastete.lazypizza.domain.entity.Product
import com.raulastete.lazypizza.domain.entity.Topping
import kotlinx.coroutines.flow.Flow

interface MenuRepository {

    fun getProductsByCategory(): Flow<Map<Category, List<Product>>>
    fun getToppings(): Flow<List<Topping>>
    fun getProductById(productId: String): Flow<Product?>
}