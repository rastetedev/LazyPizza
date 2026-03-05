package com.raulastete.lazypizza.presentation.screens.home.mapper

import com.raulastete.lazypizza.domain.PIZZA_CATEGORY_ID
import com.raulastete.lazypizza.domain.Product
import com.raulastete.lazypizza.presentation.model.ComplementFoodUi
import com.raulastete.lazypizza.presentation.model.PizzaUi
import com.raulastete.lazypizza.presentation.model.ProductUi
import com.raulastete.lazypizza.presentation.util.formatCurrency
import java.math.BigDecimal

fun Product.toUi(count: Int = 0, categoryId: String): ProductUi {

    val formattedUnitPrice = unitPrice.formatCurrency()
    val totalAmount = unitPrice.multiply(BigDecimal(count))
    val formattedTotalPrice = totalAmount.formatCurrency()

    return if (categoryId == PIZZA_CATEGORY_ID) {
        PizzaUi(
            id = id,
            image = imageUrl,
            name = name,
            ingredients = description ?: "",
            unitPrice = formattedUnitPrice
        )
    } else {
        ComplementFoodUi(
            id = id,
            image = imageUrl,
            name = name,
            unitPrice = formattedUnitPrice,
            totalPrice = if (count > 0) formattedTotalPrice else formattedUnitPrice,
            count = count
        )
    }
}
