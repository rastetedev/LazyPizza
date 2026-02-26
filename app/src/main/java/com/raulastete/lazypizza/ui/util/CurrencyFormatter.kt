package com.raulastete.lazypizza.ui.util

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US).apply {
    maximumFractionDigits = 2
    minimumFractionDigits = 2
}

fun BigDecimal.formatCurrency(): String {
    return currencyFormatter.format(this)
}
