package com.raulastete.lazypizza.domain.entity

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class OrderItem(
    val id: String = Uuid.random().toHexString(),
    val product: Product,
    val count: Int
)