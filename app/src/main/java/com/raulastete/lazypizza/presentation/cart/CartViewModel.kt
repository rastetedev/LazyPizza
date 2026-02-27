package com.raulastete.lazypizza.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulastete.lazypizza.domain.ProductRepository
import com.raulastete.lazypizza.presentation.model.CartItemUi
import com.raulastete.lazypizza.presentation.model.RecommendedProductUi
import com.raulastete.lazypizza.ui.util.formatCurrency
import kotlinx.coroutines.flow.*
import java.math.BigDecimal

class CartViewModel(
    productRepository: ProductRepository
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItemUi>>(emptyList())
    private val _isLoading = MutableStateFlow(true)

    init {
        // Mock data for initial layout testing
        _cartItems.value = listOf(
            CartItemUi(
                id = "1",
                name = "Margherita",
                image = "",
                unitPrice = "$10.99",
                totalPrice = "$21.98",
                count = 2,
                extras = listOf("1 x Extra Cheese", "1 x Pepperoni")
            ),
            CartItemUi(
                id = "2",
                name = "Pepsi",
                image = "",
                unitPrice = "$1.99",
                totalPrice = "$3.98",
                count = 2
            ),
            CartItemUi(
                id = "3",
                name = "Cookies Ice Cream",
                image = "",
                unitPrice = "$1.49",
                totalPrice = "$1.49",
                count = 1
            )
        )
        _isLoading.value = false
    }

    val uiState: StateFlow<CartUiState> = combine(
        _cartItems,
        productRepository.getProductsByCategory(), // Using this to get some recommended products
        _isLoading
    ) { cartItems, productsByCategory, isLoading ->
        
        val recommended = productsByCategory.values.flatten()
            .take(5)
            .map { 
                RecommendedProductUi(
                    id = it.id,
                    name = it.name,
                    image = it.imageUrl,
                    unitPrice = it.unitPrice.formatCurrency()
                )
            }

        val totalAmount = cartItems.sumOf { item ->
            // In a real app we would store BigDecimal in CartItemUi or fetch from domain
            val price = item.totalPrice.replace("$", "").toBigDecimalOrNull() ?: BigDecimal.ZERO
            price
        }

        CartUiState(
            cartItems = cartItems,
            recommendedProducts = recommended,
            totalPrice = totalAmount.formatCurrency(),
            isLoading = isLoading
        )
    }
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CartUiState()
    )

    fun onAction(action: CartAction) {
        when (action) {
            is CartAction.OnIncrementQuantity -> {
                _cartItems.update { items ->
                    items.map { 
                        if (it.id == action.productId) it.copy(count = it.count + 1) else it
                    }
                }
            }
            is CartAction.OnDecreaseQuantity -> {
                _cartItems.update { items ->
                    items.map { 
                        if (it.id == action.productId && it.count > 1) it.copy(count = it.count - 1) else it
                    }
                }
            }
            is CartAction.OnRemoveFromCart -> {
                _cartItems.update { it.filter { item -> item.id != action.productId } }
            }
            else -> {}
        }
    }
}
