package com.raulastete.lazypizza.presentation.menu

import com.raulastete.lazypizza.domain.entity.Category
import com.raulastete.lazypizza.presentation.ui.model.MenuCardUi
import kotlin.collections.map

data class MenuUiState(
    val isLoading: Boolean = false,
    val isSearching: Boolean = false,
    val searchProductQuery: String = "",
    val showEmptyResultsForQuery: Boolean = false,
    val menuByCategory: Map<Category, List<MenuCardUi>> = emptyMap(),
) {
    val categoryNameList = menuByCategory.keys
        .map {
            it.name
                .lowercase()
                .replaceFirstChar { firstLetter -> firstLetter.uppercase() }
        }
        .toList()
}