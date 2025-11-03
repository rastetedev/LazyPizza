package com.raulastete.lazypizza.presentation.pizza_detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulastete.lazypizza.domain.entity.Topping
import com.raulastete.lazypizza.domain.repository.CartRepository
import com.raulastete.lazypizza.domain.repository.MenuRepository
import com.raulastete.lazypizza.presentation.ui.model.PizzaCardUi
import com.raulastete.lazypizza.presentation.ui.model.ToppingCardUi
import com.raulastete.lazypizza.presentation.ui.navigation.MenuRoute.Pizza.Companion.PIZZA_ID_ARG
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
    private val cartRepository: CartRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(PizzaDetailUiState(isLoading = true))
    val uiState: StateFlow<PizzaDetailUiState> = _uiState.asStateFlow()

    private val userId = "me"

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
                                    product = product
                                ),
                                toppingCardUis = toppings.map { topping ->
                                    ToppingCardUi(
                                        topping = topping,
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
            currentState.copy(
                toppingCardUis = currentState.toppingCardUis.map { toppingCardUi ->
                    if (toppingCardUi.topping.id == toppingId) {
                        toppingCardUi.copy(count = toppingCardUi.count + 1)
                    } else toppingCardUi
                }
            )
        }
    }

    fun increaseToppingQuantity(toppingId: String) {
        _uiState.update { currentState ->
            currentState.copy(
                toppingCardUis = currentState.toppingCardUis.map { toppingCardUi ->
                    if (toppingCardUi.topping.id == toppingId) {
                        if (toppingCardUi.count >= 3) return@map toppingCardUi
                        toppingCardUi.copy(count = toppingCardUi.count + 1)
                    } else toppingCardUi
                }
            )
        }
    }

    fun decreaseToppingQuantity(toppingId: String) {
        _uiState.update { currentState ->
            currentState.copy(
                toppingCardUis = currentState.toppingCardUis.map { toppingCardUi ->
                    if (toppingCardUi.topping.id == toppingId) {
                        if (toppingCardUi.count <= 0) return@map toppingCardUi
                        toppingCardUi.copy(count = toppingCardUi.count - 1)
                    } else toppingCardUi
                }
            )
        }
    }

    fun addPizzaToCart() {
        viewModelScope.launch {
            val selectedToppingsMap = mutableMapOf<Topping, Int>()

            _uiState.value.toppingCardUis.filter { it.count > 0} .forEach { toppingCardUi ->
                selectedToppingsMap.put(toppingCardUi.topping, toppingCardUi.count)
            }

            cartRepository.addOrUpdatePizzaInCart(
                product = _uiState.value.pizzaUi!!.product,
                toppings = selectedToppingsMap,
                userId = userId
            )
        }
    }
}

data class PizzaDetailUiState(
    val isLoading: Boolean = false,
    val pizzaUi: PizzaCardUi? = null,
    val toppingCardUis: List<ToppingCardUi> = emptyList(),
    val totalPrice: String = ""
)
