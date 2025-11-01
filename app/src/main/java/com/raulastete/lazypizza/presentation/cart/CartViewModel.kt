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

                    val toppingsTotal = orderItem.toppings?.map { (topping, count) ->
                        topping.unitPrice * count
                    }?.sum() ?: 0.0

                    val orderUnitPrice = (orderItem.product.unitPrice + toppingsTotal)

                    val totalPrice =
                        orderItem.count * orderUnitPrice

                    OrderItemCardUi(
                        id = orderItem.id,
                        name = orderItem.product.name,
                        imageUrl = orderItem.product.imageUrl,
                        unitPrice = "$${orderUnitPrice}",
                        count = orderItem.count,
                        totalPrice = "$${
                            String.format(
                                Locale.US,
                                "%.2f",
                                totalPrice
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
                        recommendedItems = filteredRecommendedItemsUi,
                        totalPrice = "$${
                            String.format(
                                Locale.US,
                                "%.2f",
                                orderItemsUi.sumOf { it.totalPrice.removePrefix("$").toDouble() })
                        }"
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
    val recommendedItems: List<Product> = emptyList(),
    val totalPrice: String = ""
)