package com.raulastete.lazypizza.data.repository

import com.raulastete.lazypizza.data.remote.MenuRemoteDataSource
import com.raulastete.lazypizza.domain.entity.Order
import com.raulastete.lazypizza.domain.repository.OrderRepository

class DefaultOrderRepository(
    private val remoteDataSource: MenuRemoteDataSource
) : OrderRepository {
    override suspend fun confirmOrder(order: Order) {
        TODO("Not yet implemented")
    }

    override suspend fun getOrdersByUser(): List<Order> {
        TODO("Not yet implemented")
    }
}