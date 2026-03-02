package com.raulastete.lazypizza.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulastete.lazypizza.domain.CartItem
import com.raulastete.lazypizza.domain.CartRepository
import com.raulastete.lazypizza.domain.ProductRepository
import com.raulastete.lazypizza.presentation.model.CartItemUi
import com.raulastete.lazypizza.presentation.model.RecommendedProductUi
import com.raulastete.lazypizza.ui.util.formatCurrency
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.math.BigDecimal

class CartViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    val uiState: StateFlow<CartUiState> = combine(
        cartRepository.getCartItems(),
        productRepository.getProductsByCategory()
    ) { cartItems, productsByCategory ->

        val complementProducts = productsByCategory
            .filterKeys { !it.name.contains("pizza", ignoreCase = true) }
            .values
            .flatten()

        val cartProductIds = cartItems.map { it.product.id }.toSet()
        val recommended = complementProducts
            .filter { it.id !in cartProductIds }
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
            val itemPrice = item.product.unitPrice.multiply(BigDecimal(item.quantity))
            val toppingsPrice = item.extras?.entries?.sumOf { (topping, count) ->
                topping.unitPrice.multiply(BigDecimal(count))
            } ?: BigDecimal.ZERO
            itemPrice.add(toppingsPrice)
        }

        CartUiState(
            cartItems = cartItems.map { item ->
                val toppingsInfo = item.extras?.entries?.map { (topping, count) ->
                    "$count x ${topping.name}"
                } ?: emptyList()

                val itemTotal = item.product.unitPrice.multiply(BigDecimal(item.quantity))
                val toppingsTotal = item.extras?.entries?.sumOf { (topping, count) ->
                    topping.unitPrice.multiply(BigDecimal(count))
                } ?: BigDecimal.ZERO

                CartItemUi(
                    id = item.id,
                    name = item.product.name,
                    image = item.product.imageUrl,
                    unitPrice = item.product.unitPrice.formatCurrency(),
                    totalPrice = itemTotal.add(toppingsTotal).formatCurrency(),
                    count = item.quantity,
                    extras = toppingsInfo
                )
            },
            recommendedProducts = recommended,
            totalPrice = totalAmount.formatCurrency(),
            isLoading = false
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CartUiState(isLoading = true)
        )

    fun onAction(action: CartAction) {
        viewModelScope.launch {
            when (action) {
                is CartAction.OnIncrementQuantity -> {
                    val items = cartRepository.getCartItems().first()
                    items.find { it.id == action.productId }?.let {
                        cartRepository.addOrUpdateItem(it.copy(quantity = it.quantity + 1))
                    }
                }

                is CartAction.OnDecreaseQuantity -> {
                    val items = cartRepository.getCartItems().first()
                    items.find { it.id == action.productId }?.let {
                        if (it.quantity > 1) {
                            cartRepository.addOrUpdateItem(it.copy(quantity = it.quantity - 1))
                        } else {
                            cartRepository.deleteItem(it.id)
                        }
                    }
                }

                is CartAction.OnRemoveFromCart -> {
                    cartRepository.deleteItem(action.productId)
                }

                is CartAction.OnClickProceedToCheckout -> {

                }

                is CartAction.OnAddToCartRecommended -> {
                    val product = productRepository.getProductsByCategory().first().values.flatten()
                        .find { it.id == action.productId }
                    product?.let {
                        cartRepository.addOrUpdateItem(
                            CartItem(
                                id = it.id,
                                product = it,
                                quantity = 1
                            )
                        )
                    }
                }

                else -> {}
            }
        }
    }
}
