package com.raulastete.lazypizza.domain.entity

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Order(
    val id: String?,
    val localId: String = Uuid.random().toHexString(),
    val items: List<OrderItem>,
    val timestamp: Long,
    val customerId: String
)