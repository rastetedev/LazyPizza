package com.raulastete.lazypizza.presentation.pizza_detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulastete.lazypizza.domain.repository.MenuRepository
import com.raulastete.lazypizza.presentation.ui.model.PizzaCardUi
import com.raulastete.lazypizza.presentation.ui.model.ToppingCardUi
import com.raulastete.lazypizza.presentation.ui.navigation.menu_navigation.PizzaDetailDestination.Companion.PIZZA_ID_ARG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PizzaDetailViewModel(
    private val menuRepository: MenuRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(PizzaDetailUiState(isLoading = true))
    val uiState: StateFlow<PizzaDetailUiState> = _uiState.asStateFlow()

    init {
        val productId = savedStateHandle.get<String>(PIZZA_ID_ARG)
        if (productId != null) {
            viewModelScope.launch {

                val pizzaFlow = menuRepository.getProductById(productId)
                    .catch { exception ->
                        Log.e("PizzaDetailViewModel", "Error fetching pizza detail", exception)
                        emit(null) // Emit null on error to not break the combine
                    }

                val toppingsFlow = menuRepository.getToppings()
                    .catch { exception ->
                        Log.e("PizzaDetailViewModel", "Error fetching toppings", exception)
                        emit(emptyList()) // Emit empty list on error
                    }

                combine(pizzaFlow, toppingsFlow) { product, toppings ->
                    Pair(product, toppings)
                }.collectLatest { (product, toppings) ->
                    product?.let {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                pizzaUi = PizzaCardUi(
                                    id = product.id,
                                    imageUrl = product.imageUrl,
                                    name = product.name,
                                    description = product.description,
                                    unitPrice = "$${product.unitPrice}",
                                ),
                                toppings = toppings.map { topping ->
                                    ToppingCardUi(
                                        id = topping.id,
                                        imageUrl = topping.imageUrl,
                                        name = topping.name,
                                        unitPrice = "$${topping.price}",
                                        count = 0
                                    )
                                }
                            )
                        }
                    }
                }
            }
        } else {
            // Handle case where pizzaId is null
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun selectTopping(toppingId: String) {
        _uiState.update { currentState ->
            val updatedToppings = currentState.toppings.map { topping ->
                if (topping.id == toppingId) {
                    topping.copy(count = 1)
                } else {
                    topping
                }
            }

            currentState.copy(toppings = updatedToppings)
        }
    }

    fun increaseToppingQuantity(toppingId: String) {
        _uiState.update { currentState ->
            val updatedToppings = currentState.toppings.map { topping ->
                if (topping.id == toppingId) {
                    if (topping.count >= 3) return@map topping
                    topping.copy(count = topping.count + 1)
                } else {
                    topping
                }
            }

            currentState.copy(toppings = updatedToppings)
        }
    }

    fun decreaseToppingQuantity(toppingId: String) {
        _uiState.update { currentState ->
            val updatedToppings = currentState.toppings.map { topping ->
                if (topping.id == toppingId) {
                    topping.copy(count = topping.count - 1)
                } else {
                    topping
                }
            }

            currentState.copy(toppings = updatedToppings)
        }
    }
}

data class PizzaDetailUiState(
    val isLoading: Boolean = false,
    val pizzaUi: PizzaCardUi? = null,
    val toppings: List<ToppingCardUi> = emptyList(),
    val totalPrice: String = ""
)
