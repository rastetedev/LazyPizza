package com.raulastete.lazypizza.data.remote

import com.google.firebase.database.FirebaseDatabase
import com.raulastete.lazypizza.data.remote.dto.OrderDto
import com.raulastete.lazypizza.data.remote.dto.ProductDto
import com.raulastete.lazypizza.data.remote.dto.ToppingDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class OrderRemoteDataSource(
    database: FirebaseDatabase
) {

    private val ordersRef = database.getReference("orders")

    fun getOrdersByUser(userId: String): Flow<List<ToppingDto>> = callbackFlow {

    }

    fun confirmOrder(orderDto: OrderDto): Flow<List<ProductDto>> = callbackFlow {

    }
}
