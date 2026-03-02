package com.raulastete.lazypizza.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulastete.lazypizza.domain.CartItem
import com.raulastete.lazypizza.domain.CartRepository
import com.raulastete.lazypizza.domain.ProductRepository
import com.raulastete.lazypizza.presentation.mapper.toUi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")

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
        cartRepository.getCartItems()
    ) { filtered, query, cartItems ->
        HomeUiState(
            categories = filtered.keys.map { it.name.lowercase().replaceFirstChar { char -> char.uppercase() } },
            productsByCategory = filtered.map { (category, products) ->
                category.name.uppercase() to products.map { product ->
                    val cartItem = cartItems.find { it.product.id == product.id }
                    product.toUi(count = cartItem?.quantity ?: 0)
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
            is HomeAction.OnAddToCartComplementFoodCard -> onAddToCart(action.complementFoodId)
            is HomeAction.OnIncrementQuantityComplementFood -> onIncrementQuantity(action.complementFoodId)
            is HomeAction.OnDecreaseQuantityComplementFood -> onDecreaseQuantity(action.complementFoodId)
            is HomeAction.OnRemoveFromCartComplementFood -> onRemoveFromCart(action.complementFoodId)
            is HomeAction.OnClickCategoryChip -> TODO()
            HomeAction.OnClickLogoutButton -> TODO()
            is HomeAction.OnClickPizzaCard -> TODO()
        }
    }

    private fun onAddToCart(productId: String) {
        viewModelScope.launch {
            val product = _baseProducts.value.values.flatten().find { it.id == productId }
            product?.let {
                cartRepository.addOrUpdateItem(
                    CartItem(id = it.id, product = it, quantity = 1)
                )
            }
        }
    }

    private fun onIncrementQuantity(productId: String) {
        viewModelScope.launch {
            val cartItem = cartRepository.getCartItems().first().find { it.product.id == productId }
            cartItem?.let {
                cartRepository.addOrUpdateItem(it.copy(quantity = it.quantity + 1))
            }
        }
    }

    private fun onDecreaseQuantity(productId: String) {
        viewModelScope.launch {
            val cartItem = cartRepository.getCartItems().first().find { it.product.id == productId }
            cartItem?.let {
                if (it.quantity > 1) {
                    cartRepository.addOrUpdateItem(it.copy(quantity = it.quantity - 1))
                } else {
                    cartRepository.deleteItem(it.id)
                }
            }
        }
    }

    private fun onRemoveFromCart(productId: String) {
        viewModelScope.launch {
            cartRepository.deleteItem(productId)
        }
    }
}
