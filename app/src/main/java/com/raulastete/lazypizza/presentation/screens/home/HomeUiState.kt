package com.raulastete.lazypizza.presentation.screens.home

import com.raulastete.lazypizza.presentation.model.ProductUi

data class HomeUiState(
    val searchQuery: String = "",
    val categories: List<String> = emptyList(),
    val productsByCategory: Map<String, List<ProductUi>> = emptyMap(),
    val isLoading: Boolean = true
)
