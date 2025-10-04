package com.raulastete.lazypizza.presentation.home

import androidx.lifecycle.ViewModel
import com.raulastete.lazypizza.presentation.home.model.ProductUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private var _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

}

data class HomeUiState(
    val isLoading: Boolean = false,
    val data: Map<String, List<ProductUi>> = emptyMap(),
)