package com.raulastete.lazypizza.presentation.mapper

import com.raulastete.lazypizza.domain.Product
import com.raulastete.lazypizza.presentation.model.ComplementFoodUi
import com.raulastete.lazypizza.presentation.model.PizzaUi
import com.raulastete.lazypizza.presentation.model.ProductUi
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

fun Product.toUi(count: Int = 0): ProductUi {
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US).apply {
        maximumFractionDigits = 2
        minimumFractionDigits = 2
    }
    
    val formattedUnitPrice = currencyFormatter.format(unitPrice)
    
    // Calculamos el precio total basado en la cantidad
    val totalAmount = unitPrice.multiply(BigDecimal(count))
    val formattedTotalPrice = currencyFormatter.format(totalAmount)

    return if (category.name.contains("pizza", ignoreCase = true)) {
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
