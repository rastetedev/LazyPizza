package com.raulastete.lazypizza.data.repository

import com.raulastete.lazypizza.data.local.dao.OrderItemDao
import com.raulastete.lazypizza.data.local.dto.OrderItemDto
import com.raulastete.lazypizza.domain.entity.OrderItem
import com.raulastete.lazypizza.domain.entity.Product
import com.raulastete.lazypizza.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultCartRepository(
    private val orderItemDao: OrderItemDao
) : CartRepository {
    override suspend fun getCartItemsCountByUser(userId: String): Flow<Int> {
        return getOrderItemsByUser(userId).map { orderItems ->
            orderItems.sumOf { it.count }
        }
    }

    //TODO: Handle later when product is not found, maybe not available in the menu
    override suspend fun getOrderItemsByUser(userId: String): Flow<List<OrderItem>> {

        return orderItemDao.getOrderItemsByUser(userId).map { orderItemsDto ->
            orderItemsDto.map {
                OrderItem(
                    id = it.id,
                    product = Product(
                        id = it.productId,
                        name = it.productName,
                        description = "",
                        imageUrl = it.productImage,
                        unitPrice = it.unitPrice,
                        categoryId = it.productCategoryId,
                    ),
                    count = it.count
                )
            }
        }
    }

    override suspend fun addGenericProductToCart(
        product: Product,
        userId: String
    ) {
        orderItemDao.upsertOrderItem(
            OrderItemDto(
                productId = product.id,
                productCategoryId = product.categoryId,
                userId = userId,
                productName = product.name,
                productImage = product.imageUrl,
                count = 1,
                unitPrice = product.unitPrice
            )
        )
    }

    override suspend fun removeGenericProductFromCart(
        productId: String,
        userId: String
    ) {
        orderItemDao.deleteOrderItemByProductId(
            productId = productId,
            userId = userId
        )
    }

    override suspend fun removeOrderItem(orderItemId: Long) {
        orderItemDao.deleteOrderItem(orderItemId)
    }

    override suspend fun updateGenericProductCount(
        productId: String,
        userId: String,
        count: Int
    ) {

        val orderItemDto =
            orderItemDao.getOrderItemByProductId(productId = productId, userId = userId)

        if (orderItemDto != null) {
            orderItemDao.upsertOrderItem(
                orderItemDto.copy(
                    count = count
                )
            )
        }
    }

    override suspend fun updateOrderItemCount(orderItemId: Long, count: Int) {
        val orderItemDto =
            orderItemDao.getOrderItemById(orderItemId)

        if (orderItemDto != null) {
            orderItemDao.upsertOrderItem(
                orderItemDto.copy(
                    count = count
                )
            )
        }
    }
}