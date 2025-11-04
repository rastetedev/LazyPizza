package com.raulastete.lazypizza.data.repository

import com.raulastete.lazypizza.data.local.dao.OrderItemDao
import com.raulastete.lazypizza.data.local.dao.OrderItemToppingDao
import com.raulastete.lazypizza.data.local.dto.OrderItemDto
import com.raulastete.lazypizza.data.local.dto.OrderItemToppingDto
import com.raulastete.lazypizza.domain.entity.OrderItem
import com.raulastete.lazypizza.domain.entity.Product
import com.raulastete.lazypizza.domain.entity.Topping
import com.raulastete.lazypizza.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class DefaultCartRepository(
    private val orderItemDao: OrderItemDao,
    private val orderItemToppingDao: OrderItemToppingDao
) : CartRepository {
    override suspend fun getCartItemsCountByUser(userId: String): Flow<Int> {
        return getOrderItemsByUser(userId).map { orderItems ->
            orderItems.sumOf { it.count }
        }
    }

    //TODO: Handle later when product is not found, maybe not available in the menu
    override suspend fun getOrderItemsByUser(userId: String): Flow<List<OrderItem>> {

        return orderItemDao.getOrderItemsByUser(userId).map { orderItemsDto ->

            orderItemsDto.map { dto ->

                val toppingDtos = orderItemToppingDao.getToppingsByOrderItem(dto.id)

                val toppingsMap = toppingDtos.associate { toppingDto ->

                    val topping = Topping(
                        id = toppingDto.toppingId,
                        name = toppingDto.toppingName,
                        imageUrl = "",
                        unitPrice = toppingDto.price
                    )

                    topping to toppingDto.count
                }

                OrderItem(
                    id = dto.id,
                    product = Product(
                        id = dto.productId,
                        name = dto.productName,
                        description = "",
                        imageUrl = dto.productImage,
                        unitPrice = dto.unitPrice,
                        categoryId = dto.productCategoryId,
                    ),
                    toppings = toppingsMap,
                    count = dto.count
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

    override suspend fun addOrUpdatePizzaInCart(
        product: Product,
        toppings: Map<Topping, Int>,
        userId: String
    ): Boolean {

        val existingOrderItems = orderItemDao.getOrderItemsByProductId(product.id, userId)

        for (item in existingOrderItems) {
            val toppingsInCart = orderItemToppingDao.getToppingsByOrderItem(item.id)
            val toppingsInCartIds = toppingsInCart.associate { it.toppingId to it.count }
            val newToppingsIds = toppings.entries.associate { it.key.id to it.value }

            if (toppingsInCartIds == newToppingsIds) {
                val updatedItem = item.copy(count = item.count + 1)
                orderItemDao.upsertOrderItem(updatedItem)
                return true
            }
        }

        val newOrderItem = OrderItemDto(
            productId = product.id,
            productCategoryId = product.categoryId,
            userId = userId,
            productName = product.name,
            productImage = product.imageUrl,
            count = 1,
            unitPrice = product.unitPrice
        )
        val newOrderItemId = orderItemDao.upsertOrderItem(newOrderItem)
        if (newOrderItemId == -1L) {
            return false
        }

        val orderItemToppings = toppings.map { (topping, count) ->
            OrderItemToppingDto(
                toppingId = topping.id,
                toppingName = topping.name,
                count = count,
                price = topping.unitPrice,
                orderItemId = newOrderItemId
            )
        }

        orderItemToppingDao.insertToppings(orderItemToppings)

        return true
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

        orderItemToppingDao.deleteToppings(orderItemId)
    }

    override suspend fun updateGenericProductCount(
        productId: String,
        userId: String,
        count: Int
    ) {
        val orderItemDto =
            orderItemDao.getOrderItemsByProductId(productId = productId, userId = userId)
                .firstOrNull()

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