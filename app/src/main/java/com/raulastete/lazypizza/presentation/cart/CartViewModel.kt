package com.raulastete.lazypizza.presentation.cart

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulastete.lazypizza.domain.entity.Product
import com.raulastete.lazypizza.domain.repository.CartRepository
import com.raulastete.lazypizza.domain.repository.MenuRepository
import com.raulastete.lazypizza.presentation.ui.model.OrderItemCardUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class CartViewModel(
    private val menuRepository: MenuRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _cartUiState = MutableStateFlow(CartUiState())
    val cartUiState: StateFlow<CartUiState> = _cartUiState.asStateFlow()

    private val shuffledRecommendedProducts = mutableStateListOf<Product>()

    private val userId = "me" //TODO: Refactor later when authentication

    init {
        loadInitialRecommended()
        loadCart()
    }

    private fun loadInitialRecommended() {
        viewModelScope.launch {
            val recommended = menuRepository.getRecommendedProducts().firstOrNull() ?: emptyList()
            shuffledRecommendedProducts.addAll(recommended.shuffled())
        }
    }

    private fun loadCart() {


        viewModelScope.launch {
            cartRepository.getOrderItemsByUser(userId).collectLatest { orderItems ->

                val orderItemsUi = orderItems.map { orderItem ->
                    OrderItemCardUi(
                        id = orderItem.id,
                        name = orderItem.product.name,
                        imageUrl = orderItem.product.imageUrl,
                        unitPrice = "$${orderItem.product.unitPrice}",
                        count = orderItem.count,
                        totalPrice = "$${
                            String.format(
                                Locale.US,
                                "%.2f",
                                orderItem.count * orderItem.product.unitPrice
                            )
                        }",
                        toppings = orderItem.toppings?.map { (topping, count) ->
                            "$count x ${topping.name}"
                        },
                    )
                }

                val productIdsInCart = orderItems.map { it.product.id }.toSet()

                val filteredRecommendedItemsUi = shuffledRecommendedProducts
                    .filter { recommendedProduct ->
                        recommendedProduct.id !in productIdsInCart
                    }

                _cartUiState.update { currentState ->
                    currentState.copy(
                        orderItems = orderItemsUi,
                        recommendedItems = filteredRecommendedItemsUi
                    )
                }
            }
        }
    }

    fun addRecommendedProductToCart(product: Product) {
        viewModelScope.launch {
            cartRepository.addGenericProductToCart(
                product = product,
                userId = userId
            )
        }
    }

    fun removeOrderItemFromCart(orderItemId: Long) {
        viewModelScope.launch {
            cartRepository.removeOrderItem(orderItemId)
        }
    }

    fun decreaseOrderItemCount(orderItemId: Long, currentCount: Int) {
        viewModelScope.launch {
            cartRepository.updateOrderItemCount(orderItemId, currentCount - 1)
        }
    }

    fun increaseOrderItemCount(orderItemId: Long, currentCount: Int) {
        viewModelScope.launch {
            cartRepository.updateOrderItemCount(orderItemId, currentCount + 1)
        }
    }
}

data class CartUiState(
    val orderItems: List<OrderItemCardUi> = emptyList(),
    val recommendedItems: List<Product> = emptyList()
)