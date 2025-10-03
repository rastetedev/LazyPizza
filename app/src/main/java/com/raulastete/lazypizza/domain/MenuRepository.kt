package com.raulastete.lazypizza.domain

import com.raulastete.lazypizza.data.remote.dto.CategoryDto
import com.raulastete.lazypizza.data.remote.dto.ProductDto
import com.raulastete.lazypizza.domain.entity.Topping
import kotlinx.coroutines.flow.Flow

interface MenuRepository {

    fun getMenuData(): Flow<Map<CategoryDto, List<ProductDto>>>
    fun getToppings(): Flow<List<Topping>>
}