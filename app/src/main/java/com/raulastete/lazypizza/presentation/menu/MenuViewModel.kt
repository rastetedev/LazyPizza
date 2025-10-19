package com.raulastete.lazypizza.presentation.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulastete.lazypizza.domain.MenuRepository
import com.raulastete.lazypizza.domain.entity.Category
import com.raulastete.lazypizza.domain.entity.Category.Companion.PIZZA_CATEGORY_ID
import com.raulastete.lazypizza.domain.entity.Product
import com.raulastete.lazypizza.presentation.ui.model.ProductCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.filter
import kotlin.collections.map

class MenuViewModel(
    private val menuRepository: MenuRepository
) : ViewModel() {

    private var _uiState = MutableStateFlow(MenuUiState())
    val uiState = _uiState.asStateFlow()

    private var completeData: Map<Category, List<ProductCard>> = emptyMap()

    init {
        fetchProductsByCategory()
    }

    private fun fetchProductsByCategory() {
        viewModelScope.launch {
            menuRepository.getProductsByCategory()
                .onStart {
                    _uiState.update { it.copy(isLoading = true) }
                }
                .catch { exception ->
                    Log.e("Home", exception.message, exception)
                    _uiState.update { it.copy(isLoading = false) }
                }
                .collect { data ->

                    completeData = transformToUiModel(data)

                    _uiState.update { it.copy(menuByCategory = completeData, isLoading = false) }
                }
        }
    }

    private fun transformToUiModel(data: Map<Category, List<Product>>): Map<Category, List<ProductCard>> {
        return data.map { (category, products) ->
            val products = if (category.id == PIZZA_CATEGORY_ID) {
                products.map { product ->
                    ProductCard.PizzaCard(
                        id = product.id,
                        imageUrl = product.imageUrl,
                        name = product.name,
                        price = product.price,
                        description = product.description
                    )
                }
            } else {
                products.map { product ->
                    ProductCard.GenericProductCard(
                        id = product.id,
                        imageUrl = product.imageUrl,
                        name = product.name,
                        price = product.price,
                    )
                }
            }
            category.copy(
                name = category.name.uppercase(),
            ) to products
        }.toMap()
    }

    fun searchProduct(query: String) {
        if (query.isEmpty()) _uiState.update { it.copy(searchProductQuery = "", menuByCategory = completeData) }
        _uiState.update { it.copy(searchProductQuery = query) }
        filterData(query)
    }

    fun increaseGenericProductCount(genericProductId: String) {
        _uiState.update { state ->
            val updatedData =
                state.menuByCategory.map { (category: Category, productList: List<ProductCard>) ->
                    val updatedProductList = productList
                        .map { productCard ->
                            if (productCard.id == genericProductId) {
                                (productCard as ProductCard.GenericProductCard).copy(count = productCard.count + 1)
                            } else {
                                productCard
                            }
                        }
                    category to updatedProductList
                }.toMap()

            state.copy(menuByCategory = updatedData)
        }
    }

    fun removeGenericProductFromCard(genericProductId: String) {
        _uiState.update { state ->
            val updatedData =
                state.menuByCategory.map { (category: Category, productList: List<ProductCard>) ->
                    val updatedProductList = productList
                        .map { productCard ->
                            if (productCard.id == genericProductId) {
                                (productCard as ProductCard.GenericProductCard).copy(count = 0)
                            } else {
                                productCard
                            }
                        }
                    category to updatedProductList
                }.toMap()

            state.copy(menuByCategory = updatedData)
        }
    }


    fun decreaseGenericProductCount(genericProductId: String) {
        _uiState.update { state ->
            val updatedData =
                state.menuByCategory.map { (category: Category, productList: List<ProductCard>) ->
                    val updatedProductList = productList
                        .map { productCard ->
                            if (productCard.id == genericProductId) {
                                (productCard as ProductCard.GenericProductCard).copy(count = productCard.count - 1)
                            } else {
                                productCard
                            }
                        }
                    category to updatedProductList
                }.toMap()

            state.copy(menuByCategory = updatedData)
        }
    }

    private fun filterData(productName: String) {
        _uiState.update { it.copy(isSearching = true) }

        val filteredData = completeData
            .map { (category, products) ->
                category to products.filter { product ->
                    product.name.contains(productName, ignoreCase = true)
                }
            }
            .filter { (_, products) -> products.isNotEmpty() }
            .toMap()

        if (filteredData.values.isNotEmpty()) {
            _uiState.update {
                it.copy(
                    menuByCategory = filteredData,
                    showEmptyResultsForQuery = false,
                    isSearching = false
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    menuByCategory = emptyMap(),
                    showEmptyResultsForQuery = true,
                    isSearching = false
                )
            }
        }
    }
}