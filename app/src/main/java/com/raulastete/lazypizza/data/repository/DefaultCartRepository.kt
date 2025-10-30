package com.raulastete.lazypizza.data.repository

import com.raulastete.lazypizza.data.local.OrderItemDao
import com.raulastete.lazypizza.data.local.OrderItemDto
import com.raulastete.lazypizza.data.remote.MenuRemoteDataSource
import com.raulastete.lazypizza.domain.entity.OrderItem
import com.raulastete.lazypizza.domain.entity.Product
import com.raulastete.lazypizza.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class DefaultCartRepository(
    private val orderItemDao: OrderItemDao,
    private val menuRemoteDataSource: MenuRemoteDataSource
) : CartRepository {

    //TODO: Handle later when product is not found, maybe not available in the menu
    override suspend fun getOrderItemsByUser(userId: String): Flow<List<OrderItem>> {
        return combine(
            orderItemDao.getOrderItemsByUser(userId),
            menuRemoteDataSource.getAllProducts()
        ) { orderItemsDto, productsDto ->

            orderItemsDto.mapNotNull { orderItemDto ->
                val product = productsDto.firstOrNull { it.id == orderItemDto.productId }?.let {
                    Product(
                        id = it.id,
                        name = it.name,
                        description = it.description,
                        imageUrl = it.imageUrl,
                        unitPrice = it.price,
                        categoryId = it.category
                    )
                }
                if (product != null) {
                    OrderItem(
                        id = orderItemDto.id,
                        product = product,
                        count = orderItemDto.count
                    )
                } else null
            }
        }
    }

    override suspend fun increaseProductCountInCart(productId: String) {
        val orderDto = orderItemDao.getOrderItemByProductId(productId)

        if (orderDto != null) {
            orderItemDao.upsert(
                orderDto.copy(
                    count = orderDto.count + 1
                )
            )
        } else {
            orderItemDao.upsert(
                OrderItemDto(
                    productId = productId,
                    count = 1,
                    userId = "me"
                )
            )
        }
    }

    override suspend fun decreaseProductCountInCart(productId: String) {
        val orderDto = orderItemDao.getOrderItemByProductId(productId)
        check(orderDto != null)

        if (orderDto.count == 1) {
            orderItemDao.deleteItem(orderDto.id)
        } else {
            orderItemDao.upsert(
                orderDto.copy(
                    count = orderDto.count - 1
                )
            )
        }
    }

    override suspend fun deleteProductFromCart(productId: String) {
        val orderDto = orderItemDao.getOrderItemByProductId(productId)
        check(orderDto != null)

        orderItemDao.deleteItem(orderDto.id)
    }

    override suspend fun deleteOrderItem(orderItem: OrderItem) {
        orderItemDao.deleteItem(orderItem.id)
    }
}