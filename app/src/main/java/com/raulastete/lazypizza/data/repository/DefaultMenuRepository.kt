package com.raulastete.lazypizza.data.repository

import com.raulastete.lazypizza.data.remote.MenuRemoteDataSource
import com.raulastete.lazypizza.data.remote.dto.toDomain
import com.raulastete.lazypizza.domain.entity.Category
import com.raulastete.lazypizza.domain.entity.Product
import com.raulastete.lazypizza.domain.entity.Topping
import com.raulastete.lazypizza.domain.repository.MenuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class DefaultMenuRepository(
    private val remoteDataSource: MenuRemoteDataSource
) : MenuRepository {

    override fun getMenuByCategory(): Flow<Map<Category, List<Product>>> {

        return combine(
            remoteDataSource.getAllCategories(),
            remoteDataSource.getAllProducts()
        ) { categoriesDto, productsDto ->

            val productsById = productsDto.associateBy { it.id }

            categoriesDto.associateWith { categoryDto ->
                categoryDto.products.keys.mapNotNull { productId ->
                    productsById[productId]
                }
            }.map { (categoryDto, productsDto) ->

                val category = Category(categoryDto.id, categoryDto.name)
                val products = productsDto.map { productDto ->
                    Product(
                        id = productDto.id,
                        name = productDto.name,
                        description = productDto.description,
                        unitPrice = productDto.price,
                        imageUrl = productDto.imageUrl,
                        categoryId = productDto.category
                    )
                }
                category to products
            }.toMap()
        }
    }

    override fun getRecommendedProducts(): Flow<List<Product>> {
        return remoteDataSource.getAllProducts()
            .map { productsDto ->
                productsDto
                    .map { productDto ->
                        Product(
                            id = productDto.id,
                            name = productDto.name,
                            description = productDto.description,
                            unitPrice = productDto.price,
                            imageUrl = productDto.imageUrl,
                            categoryId = productDto.category
                        )
                    }.filter { product ->
                        product.isDrink || product.isSauce
                    }
            }
    }

    override fun getToppings(): Flow<List<Topping>> {
        return remoteDataSource.getAllToppings().map { list ->
            list.map { dto -> dto.toDomain() }
        }
    }
}