package com.raulastete.lazypizza.presentation.cart

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulastete.lazypizza.domain.entity.Product
import com.raulastete.lazypizza.domain.repository.CartRepository
import com.raulastete.lazypizza.domain.repository.MenuRepository
import com.raulastete.lazypizza.presentation.ui.model.OrderItemCardUi
import com.raulastete.lazypizza.presentation.ui.model.RecommendedProductCardUi
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
            // 3. El combine ahora solo depende de los items del carrito y usa la lista local ya barajada
            cartRepository.getOrderItemsByUser("me").collectLatest { orderItems ->

                val orderItemsUi = orderItems.map { orderItem ->
                    OrderItemCardUi(
                        id = orderItem.id,
                        name = orderItem.product.name,
                        imageUrl = orderItem.product.imageUrl,
                        unitPrice = "$${orderItem.product.unitPrice}",
                        count = orderItem.count,
                        totalPrice = "$${
                            String.format(
                                Locale.US, // Es mejor usar Locale.US para formateo de precios
                                "%.2f",
                                orderItem.count * orderItem.product.unitPrice
                            )
                        }",
                        toppings = null
                    )
                }

                val productIdsInCart = orderItems.map { it.product.id }.toSet()

                // 4. Filtramos la lista local que ya está barajada
                val filteredRecommendedItemsUi = shuffledRecommendedProducts
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

                // 5. Actualizamos el estado de la UI
                _cartUiState.update { currentState ->
                    currentState.copy(
                        orderItems = orderItemsUi,
                        recommendedItems = filteredRecommendedItemsUi
                    )
                }
            }
        }
    }

    fun addProductToCart(productId: String) {
        viewModelScope.launch {
            cartRepository.increaseProductCountInCart(productId)
        }
    }

    fun removeOrderItemFromCart(orderItemId: Long) {
        viewModelScope.launch {
            cartRepository.deleteOrderItem(orderItemId)
        }
    }

    fun decreaseOrderItemCount(orderItemId: Long) {
        viewModelScope.launch {
            cartRepository.decreaseOrderItemCountInCart(orderItemId)
        }
    }

    fun increaseOrderItemCount(orderItemId: Long) {
        viewModelScope.launch {
            cartRepository.increaseOrderItemCountInCart(orderItemId)
        }
    }
}

data class CartUiState(
    val orderItems: List<OrderItemCardUi> = emptyList(),
    val recommendedItems: List<RecommendedProductCardUi> = emptyList()
)