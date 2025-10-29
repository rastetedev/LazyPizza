package com.raulastete.lazypizza.domain.repository

import com.raulastete.lazypizza.domain.entity.Order

interface OrderRepository {

    suspend fun confirmOrder(order: Order)

    suspend fun getOrdersByUser(): List<Order>

}