package com.raulastete.lazypizza.presentation.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulastete.lazypizza.domain.CartItem
import com.raulastete.lazypizza.domain.CartRepository
import com.raulastete.lazypizza.domain.PIZZA_CATEGORY_ID
import com.raulastete.lazypizza.domain.ProductRepository
import com.raulastete.lazypizza.presentation.model.CartItemUi
import com.raulastete.lazypizza.presentation.model.RecommendedProductUi
import com.raulastete.lazypizza.presentation.util.formatCurrency
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.BigDecimal
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CartViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    val uiState: StateFlow<CartUiState> = combine(
        cartRepository.getCartItems(),
        productRepository.getProductsByCategory()
    ) { cartItems, productsByCategory ->

        val complementProducts = productsByCategory
            .filter { it.id != PIZZA_CATEGORY_ID }
            .flatMap { it.products }

        val cartProductIds = cartItems.map { it.productId }
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
            val itemPrice = item.productUnitPrice.multiply(BigDecimal(item.quantity))
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

                val itemTotal = item.productUnitPrice.multiply(BigDecimal(item.quantity))
                val toppingsTotal = item.extras?.entries?.sumOf { (topping, count) ->
                    topping.unitPrice.multiply(BigDecimal(count))
                } ?: BigDecimal.ZERO

                CartItemUi(
                    id = item.id,
                    productId = item.productId,
                    name = item.productName,
                    image = item.productImageUrl,
                    unitPrice = item.productUnitPrice.formatCurrency(),
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

    @OptIn(ExperimentalUuidApi::class)
    fun onAction(action: CartAction) {
        viewModelScope.launch {
            when (action) {
                is CartAction.OnIncrementQuantity -> {
                    val items = cartRepository.getCartItems().first()
                    items.find { it.id == action.cartItemId }?.let {
                        cartRepository.addOrUpdateItem(it.copy(quantity = it.quantity + 1))
                    }
                }

                is CartAction.OnDecreaseQuantity -> {
                    val items = cartRepository.getCartItems().first()
                    items.find { it.id == action.cartItemId }?.let {
                        if (it.quantity > 1) {
                            cartRepository.addOrUpdateItem(it.copy(quantity = it.quantity - 1))
                        } else {
                            cartRepository.deleteItemBasedOnCartItem(it.id)
                        }
                    }
                }

                is CartAction.OnRemoveFromCart -> {
                    cartRepository.deleteItemBasedOnCartItem(action.cartItemId)
                }

                is CartAction.OnClickProceedToCheckout -> {

                }

                is CartAction.OnAddToCartRecommended -> {
                    val product =
                        productRepository.getProductsByCategory().first().flatMap { it.products }
                            .find { it.id == action.productId }
                    product?.let {
                        cartRepository.addOrUpdateItem(
                            CartItem(
                                id = Uuid.random().toHexString(),
                                productId = it.id,
                                productName = it.name,
                                productImageUrl = it.imageUrl,
                                productUnitPrice = it.unitPrice,
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
