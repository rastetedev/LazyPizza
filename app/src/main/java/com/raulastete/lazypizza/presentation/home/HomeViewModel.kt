package com.raulastete.lazypizza.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulastete.lazypizza.domain.MenuRepository
import com.raulastete.lazypizza.domain.entity.Category
import com.raulastete.lazypizza.domain.entity.Category.Companion.PIZZA_CATEGORY_ID
import com.raulastete.lazypizza.domain.entity.Product
import com.raulastete.lazypizza.presentation.home.model.CountableProductUi
import com.raulastete.lazypizza.presentation.home.model.PizzaUi
import com.raulastete.lazypizza.presentation.home.model.ProductUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.map

class HomeViewModel(
    private val menuRepository: MenuRepository
) : ViewModel() {

    private var _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private var completeData: Map<Category, List<ProductUi>> = emptyMap()

    init {
        fetchProductsByCategory()
    }

    private fun fetchProductsByCategory() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            menuRepository.getProductsByCategory()
                .catch { exception ->
                    Log.e("Home", exception.message, exception)
                }
                .collectLatest { data ->

                    completeData = transformToUiModel(data)

                    _uiState.update { it.copy(data = completeData, isLoading = false) }
                }
        }
    }

    private fun transformToUiModel(data: Map<Category, List<Product>>): Map<Category, List<ProductUi>> {
        return data.map { (category, products) ->
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
            category.copy(
                name = category.name.uppercase(),
            ) to products
        }.toMap()
    }

    fun search(query: String) {
        if (query.isEmpty()) _uiState.update { it.copy(searchQuery = "", data = completeData) }
        _uiState.update { it.copy(searchQuery = query) }
        filterData(query)
    }

    fun addGenericProductToCard(productId: String) {
        _uiState.update { state ->
            val updatedData = state.data.map { (category, productList) ->
                val updatedProductList = productList
                    .map { product ->
                        if (product.id == productId) {
                            (product as CountableProductUi).copy(count = product.count + 1)
                        } else {
                            product
                        }
                    }
                category to updatedProductList
            }.toMap()

            state.copy(data = updatedData)
        }
    }

    fun removeGenericProductFromCard(productId: String) {
        _uiState.update { state ->
            val updatedData = state.data.map { (category, productList) ->
                val updatedProductList = productList
                    .map { product ->
                        if (product.id == productId) {
                            (product as CountableProductUi).copy(count = 0)
                        } else {
                            product
                        }
                    }
                category to updatedProductList
            }.toMap()

            state.copy(data = updatedData)
        }
    }

    fun increaseGenericProductCount(productId: String) {
        _uiState.update { state ->
            val updatedData = state.data.map { (category, productList) ->
                val updatedProductList = productList
                    .map { product ->
                        if (product.id == productId) {
                            (product as CountableProductUi).copy(count = product.count + 1)
                        } else {
                            product
                        }
                    }
                category to updatedProductList
            }.toMap()

            state.copy(data = updatedData)
        }
    }

    fun decreaseGenericProductCount(productId: String) {
        _uiState.update { state ->
            val updatedData = state.data.map { (category, productList) ->
                val updatedProductList = productList
                    .map { product ->
                        if (product.id == productId) {
                            (product as CountableProductUi).copy(count = product.count - 1)
                        } else {
                            product
                        }
                    }
                category to updatedProductList
            }.toMap()

            state.copy(data = updatedData)
        }
    }

    private fun filterData(query: String) {
        _uiState.update { it.copy(isLoading = true) }

        val filteredData = completeData
            .map { (category, products) ->
                category to products.filter { product ->
                    product.name.contains(query, ignoreCase = true)
                }
            }
            .filter { (_, products) -> products.isNotEmpty() }
            .toMap()

        if (filteredData.values.isNotEmpty()) {
            _uiState.update {
                it.copy(
                    data = filteredData,
                    showEmptyDataMessage = false,
                    isLoading = false
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    data = emptyMap(),
                    showEmptyDataMessage = true,
                    isLoading = false
                )
            }
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val data: Map<Category, List<ProductUi>> = emptyMap(),
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