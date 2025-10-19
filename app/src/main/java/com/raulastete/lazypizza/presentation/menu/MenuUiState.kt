package com.raulastete.lazypizza.presentation.menu

import com.raulastete.lazypizza.domain.entity.Category
import com.raulastete.lazypizza.presentation.ui.model.ProductCard
import kotlin.collections.map

data class MenuUiState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val data: Map<Category, List<ProductCard>> = emptyMap(),
    val showEmptyDataMessage: Boolean = false,
) {
    val categoryNameList = data.keys
        .map {
            it.name
                .lowercase()
                .replaceFirstChar { firstLetter -> firstLetter.uppercase() }
        }
        .toList()
}