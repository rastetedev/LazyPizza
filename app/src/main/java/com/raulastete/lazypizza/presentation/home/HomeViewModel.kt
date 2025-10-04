package com.raulastete.lazypizza.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulastete.lazypizza.domain.MenuRepository
import com.raulastete.lazypizza.domain.entity.Category
import com.raulastete.lazypizza.domain.entity.Category.Companion.PIZZA_CATEGORY_ID
import com.raulastete.lazypizza.presentation.home.model.CountableProductUi
import com.raulastete.lazypizza.presentation.home.model.PizzaUi
import com.raulastete.lazypizza.presentation.home.model.ProductUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val menuRepository: MenuRepository
) : ViewModel() {

    private var _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchProductsByCategory()
    }

    private fun fetchProductsByCategory() {
        viewModelScope.launch {
            menuRepository.getProductsByCategory()
                .catch { exception ->
                    Log.e("Home", exception.message, exception)
                }
                .collectLatest { data ->
                    _uiState.update {
                        it.copy(
                            data = data.map { (category, products) ->

                                val products = if (category.id == PIZZA_CATEGORY_ID) {
                                    products.map { product ->
                                        PizzaUi(
                                            id = product.id,
                                            imageUrl = product.imageUrl,
                                            name = product.name,
                                            description = product.description,
                                            price = product.price
                                        )
                                    }
                                } else {
                                    products.map { product ->
                                        CountableProductUi(
                                            id = product.id,
                                            imageUrl = product.imageUrl,
                                            name = product.name,
                                            price = product.price,
                                            count = 0
                                        )
                                    }
                                }

                                category to products
                            }.toMap()
                        )
                    }
                }
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val data: Map<Category, List<ProductUi>> = emptyMap(),
) {
    val categoryNameList = data.keys
        .map { it.name }
        .toList()
}