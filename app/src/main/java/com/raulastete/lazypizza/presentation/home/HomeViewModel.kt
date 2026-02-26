package com.raulastete.lazypizza.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulastete.lazypizza.domain.ProductRepository
import com.raulastete.lazypizza.presentation.mapper.toUi
import kotlinx.coroutines.flow.*

class HomeViewModel(
    productRepository: ProductRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _productCounts = MutableStateFlow<Map<String, Int>>(emptyMap())

    private val _baseProducts = productRepository.getProductsByCategory()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyMap()
        )

    private val _filteredProducts = combine(_baseProducts, _searchQuery) { products, query ->
        if (query.isBlank()) {
            products
        } else {
            products.mapValues { (_, productList) ->
                productList.filter { it.name.contains(query, ignoreCase = true) }
            }.filterValues { it.isNotEmpty() }
        }
    }

    val uiState: StateFlow<HomeUiState> = combine(
        _filteredProducts,
        _searchQuery,
        _productCounts
    ) { filtered, query, counts ->
        HomeUiState(
            categories = filtered.keys.map { it.name.lowercase().replaceFirstChar { char -> char.uppercase() } },
            productsByCategory = filtered.map { (category, products) ->
                category.name.uppercase() to products.map { product ->
                    product.toUi(count = counts[product.id] ?: 0)
                }
            }.toMap(),
            searchQuery = query,
            isLoading = false
        )
    }
    .onStart { 
        if (_baseProducts.value.isEmpty()) emit(HomeUiState(isLoading = true)) 
    }
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState(isLoading = true)
    )

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnQueryProducts -> _searchQuery.value = action.query
            is HomeAction.OnAddToCartComplementFoodCard -> onIncrementQuantity(action.complementFoodId)
            is HomeAction.OnIncrementQuantityComplementFood -> onIncrementQuantity(action.complementFoodId)
            is HomeAction.OnDecreaseQuantityComplementFood -> onDecreaseQuantity(action.complementFoodId)
            is HomeAction.OnRemoveFromCartComplementFood -> onResetQuantity(action.complementFoodId)
            is HomeAction.OnClickCategoryChip -> TODO()
            HomeAction.OnClickLogoutButton -> TODO()
            is HomeAction.OnClickPizzaCard -> TODO()
        }
    }

    private fun onIncrementQuantity(id: String) {
        _productCounts.update { it + (id to (it[id] ?: 0) + 1) }
    }

    private fun onDecreaseQuantity(id: String) {
        _productCounts.update { current ->
            val count = current[id] ?: 0
            if (count > 1) current + (id to count - 1) else current - id
        }
    }

    private fun onResetQuantity(id: String) {
        _productCounts.update { it - id }
    }
}
