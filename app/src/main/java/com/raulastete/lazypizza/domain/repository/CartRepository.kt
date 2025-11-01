package com.raulastete.lazypizza.domain.repository

import com.raulastete.lazypizza.domain.entity.OrderItem
import com.raulastete.lazypizza.domain.entity.Product
import com.raulastete.lazypizza.domain.entity.Topping
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun getCartItemsCountByUser(userId: String): Flow<Int>

    suspend fun getOrderItemsByUser(userId: String): Flow<List<OrderItem>>

    suspend fun addGenericProductToCart(product: Product, userId: String)

    suspend fun addOrUpdatePizzaInCart(product: Product, toppings: Map<Topping, Int>, userId: String) : Boolean

    suspend fun removeGenericProductFromCart(productId: String, userId: String)

    suspend fun removeOrderItem(orderItemId: Long)

    suspend fun updateGenericProductCount(productId: String, userId: String, count: Int)

    suspend fun updateOrderItemCount(orderItemId: Long, count: Int)

}