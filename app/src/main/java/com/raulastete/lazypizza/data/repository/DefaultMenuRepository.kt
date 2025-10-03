package com.raulastete.lazypizza.data.repository

import com.raulastete.lazypizza.data.remote.MenuRemoteDataSource
import com.raulastete.lazypizza.data.remote.dto.CategoryDto
import com.raulastete.lazypizza.data.remote.dto.ProductDto
import com.raulastete.lazypizza.data.remote.dto.toDomain
import com.raulastete.lazypizza.domain.entity.Topping
import com.raulastete.lazypizza.domain.MenuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultMenuRepository(
    private val remoteDataSource: MenuRemoteDataSource
) : MenuRepository {
    override fun getMenuData(): Flow<Map<CategoryDto, List<ProductDto>>> {
        TODO("Not yet implemented")
    }

    override fun getToppings(): Flow<List<Topping>> {
        return remoteDataSource.getAllToppings().map { list ->
            list.map { dto ->
                dto.toDomain()
            }
        }
    }
}