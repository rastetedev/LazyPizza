package com.raulastete.lazypizza.presentation.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulastete.lazypizza.domain.repository.MenuRepository
import com.raulastete.lazypizza.domain.entity.Category
import com.raulastete.lazypizza.domain.entity.Category.Companion.PIZZA_CATEGORY_ID
import com.raulastete.lazypizza.domain.entity.OrderItem
import com.raulastete.lazypizza.domain.entity.Product
import com.raulastete.lazypizza.domain.repository.CartRepository
import com.raulastete.lazypizza.presentation.ui.model.GenericProductCardUi
import com.raulastete.lazypizza.presentation.ui.model.MenuCardUi
import com.raulastete.lazypizza.presentation.ui.model.PizzaCardUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.filter
import kotlin.collections.map

class MenuViewModel(
    private val menuRepository: MenuRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private var _uiState = MutableStateFlow(MenuUiState())
    val uiState = _uiState.asStateFlow()

    private var completeMenuBackupBeforeSearch: Map<Category, List<MenuCardUi>> = emptyMap()

    init {
        fetchProductsByCategory()
    }

    private fun fetchProductsByCategory() {
        viewModelScope.launch {
            combine(
                menuRepository.getMenuByCategory(),
                cartRepository.getOrderItemsByUser("me")
            ) { menu, cart ->
                transformToUiModel(menu, cart)
            }
                .onStart {
                    _uiState.update { it.copy(isLoading = true) }
                }
                .catch { exception ->
                    Log.e("Home", exception.message, exception)
                    _uiState.update { it.copy(isLoading = false) }
                }
                .collectLatest { data ->
                    completeMenuBackupBeforeSearch = data
                    _uiState.update {
                        it.copy(
                            menuByCategory = completeMenuBackupBeforeSearch,
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun transformToUiModel(
        data: Map<Category, List<Product>>,
        cart: List<OrderItem>
    ): Map<Category, List<MenuCardUi>> {
        return data.map { (category, products) ->
            val products = if (category.id == PIZZA_CATEGORY_ID) {
                products.map { product ->
                    PizzaCardUi(
                        id = product.id,
                        imageUrl = product.imageUrl,
                        name = product.name,
                        unitPrice = "${product.unitPrice}",
                        description = product.description
                    )
                }
            } else {
                products.map { product ->

                    val count = cart.firstOrNull { cartItem ->
                        cartItem.product.id == product.id
                    }?.count ?: 0

                    GenericProductCardUi(
                        id = product.id,
                        imageUrl = product.imageUrl,
                        name = product.name,
                        unitPrice = "${product.unitPrice}",
                        totalPrice = "${product.unitPrice}",
                        count = count
                    )
                }
            }
            category.copy(
                name = category.name.uppercase(),
            ) to products
        }.toMap()
    }

    fun searchProduct(query: String) {
        if (query.isEmpty()) {
            _uiState.update {
                it.copy(
                    searchProductQuery = "",
                    menuByCategory = completeMenuBackupBeforeSearch
                )
            }
            return
        }
        _uiState.update { it.copy(searchProductQuery = query) }
        filterData(query)
    }

    fun increaseGenericProductCount(genericProductId: String) {
        viewModelScope.launch {
            cartRepository.increaseProductCountInCart(productId = genericProductId)
        }
    }

    fun removeGenericProductFromCard(genericProductId: String) {
        viewModelScope.launch {
            cartRepository.deleteProductFromCart(productId = genericProductId)
        }
    }

    fun decreaseGenericProductCount(genericProductId: String) {
        viewModelScope.launch {
            cartRepository.decreaseProductCountInCart(productId = genericProductId)
        }
    }

    private fun filterData(productName: String) {
        _uiState.update { it.copy(isSearching = true) }

        val filteredData = completeMenuBackupBeforeSearch
            .map { (category, menuCardUis) ->
                category to menuCardUis.filter { menuCardUi ->
                    menuCardUi.name.contains(productName, ignoreCase = true)
                }
            }
            .filter { (_, menuCardUis) -> menuCardUis.isNotEmpty() }
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