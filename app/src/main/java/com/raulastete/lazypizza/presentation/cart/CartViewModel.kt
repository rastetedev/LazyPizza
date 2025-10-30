package com.raulastete.lazypizza.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulastete.lazypizza.domain.repository.CartRepository
import com.raulastete.lazypizza.domain.repository.MenuRepository
import com.raulastete.lazypizza.presentation.ui.model.OrderItemCardUi
import com.raulastete.lazypizza.presentation.ui.model.RecommendedProductCardUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class CartViewModel(
    private val menuRepository: MenuRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _cartUiState = MutableStateFlow(CartUiState())
    val cartUiState: StateFlow<CartUiState> = _cartUiState.asStateFlow()

    init {
        loadCart()
    }

    private fun loadCart() {
        viewModelScope.launch {
            combine(
                cartRepository.getOrderItemsByUser("me"),
                menuRepository.getRecommendedProducts()
            ) { orderItems, recommendedItems ->
                val orderItemsUi = orderItems.map { orderItem ->
                    OrderItemCardUi(
                        id = orderItem.id,
                        name = orderItem.product.name,
                        imageUrl = orderItem.product.imageUrl,
                        unitPrice = "$${orderItem.product.unitPrice}",
                        count = orderItem.count,
                        totalPrice = "$${
                            String.format(
                                Locale.getDefault(),
                                "%.2f",
                                orderItem.count * orderItem.product.unitPrice
                            )
                        }",
                        toppings = null
                    )
                }

                val productIdsInCart = orderItems.map { it.product.id }.toSet()

                val filteredRecommendedItemsUi = recommendedItems
                    .filter { recommendedProduct ->
                        recommendedProduct.id !in productIdsInCart
                    }
                    .map { recommendedItem ->
                        RecommendedProductCardUi(
                            id = recommendedItem.id,
                            name = recommendedItem.name,
                            imageUrl = recommendedItem.imageUrl,
                            unitPrice = "$${recommendedItem.unitPrice}",
                        )
                    }

                Pair(orderItemsUi, filteredRecommendedItemsUi)
            }
                .collectLatest { (orderItemsUi, filteredRecommendedItemsUi) ->
                    _cartUiState.update { currentState ->
                        currentState.copy(
                            orderItems = orderItemsUi,
                            recommendedItems = filteredRecommendedItemsUi
                        )
                    }
                }
        }
    }
}

data class CartUiState(
    val orderItems: List<OrderItemCardUi> = emptyList(),
    val recommendedItems: List<RecommendedProductCardUi> = emptyList()
)