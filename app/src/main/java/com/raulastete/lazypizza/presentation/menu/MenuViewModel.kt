package com.raulastete.lazypizza.presentation.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulastete.lazypizza.domain.repository.MenuRepository
import com.raulastete.lazypizza.domain.entity.Category
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
import java.util.Locale
import kotlin.collections.filter
import kotlin.collections.map

class MenuViewModel(
    private val menuRepository: MenuRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private var _uiState = MutableStateFlow(MenuUiState())
    val uiState = _uiState.asStateFlow()

    private var completeMenuBackupBeforeSearch: Map<Category, List<MenuCardUi>> = emptyMap()

    private val userId = "me" //TODO: get user id from auth

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
            val products = if (category.isPizza) {
                products.map { product ->
                    PizzaCardUi(product = product)
                }
            } else {
                products.map { product ->

                    val count = cart.firstOrNull { cartItem ->
                        cartItem.product.id == product.id
                    }?.count ?: 0

                    GenericProductCardUi(
                        product = product,
                        totalPrice = String.format(
                            Locale.US,
                            "%.2f",
                            product.unitPrice * count
                        ),
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

    fun increaseGenericProductCount(genericProductId: String, currentCount: Int) {
        viewModelScope.launch {
            cartRepository.updateGenericProductCount(
                productId = genericProductId,
                userId = userId,
                count = currentCount + 1
            )
        }
    }

    fun addGenericProductToCart(product: Product) {
        viewModelScope.launch {
            cartRepository.addGenericProductToCart(
                product = product,
                userId = userId
            )
        }
    }

    fun removeGenericProductFromCart(genericProductId: String) {
        viewModelScope.launch {
            cartRepository.removeGenericProductFromCart(
                productId = genericProductId,
                userId = userId
            )
        }
    }

    fun decreaseGenericProductCount(genericProductId: String, currentCount: Int) {
        viewModelScope.launch {
            cartRepository.updateGenericProductCount(
                productId = genericProductId,
                userId = userId,
                count = currentCount - 1
            )
        }
    }

    private fun filterData(productName: String) {
        _uiState.update { it.copy(isSearching = true) }

        val filteredData = completeMenuBackupBeforeSearch
            .map { (category, menuCardUis) ->
                category to menuCardUis.filter { menuCardUi ->
                    menuCardUi.product.name.contains(productName, ignoreCase = true)
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