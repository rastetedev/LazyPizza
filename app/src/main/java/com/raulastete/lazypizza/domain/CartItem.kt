package com.raulastete.lazypizza.domain

import java.math.BigDecimal

data class CartItem(
    val id: String,
    val productId: String,
    val productName: String,
    val productImageUrl: String,
    val productUnitPrice: BigDecimal,
    val quantity: Int,
    val extras: Map<Extra, Int>? = null,
    val addedAt: Long = System.currentTimeMillis()
) {
    val totalPrice: BigDecimal
        get() {
            val productTotal = productUnitPrice.multiply(BigDecimal(quantity))
            val toppingsTotal = extras?.entries?.sumOf { (topping, count) ->
                topping.unitPrice.multiply(BigDecimal(count))
            } ?: BigDecimal.ZERO
            return productTotal.add(toppingsTotal)
        }
}

data class Extra(
    val unitPrice: BigDecimal,
    val name: String
)
