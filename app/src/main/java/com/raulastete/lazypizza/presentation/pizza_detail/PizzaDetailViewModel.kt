package com.raulastete.lazypizza.presentation.pizza_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulastete.lazypizza.domain.entity.Product
import com.raulastete.lazypizza.domain.entity.Topping
import com.raulastete.lazypizza.domain.repository.CartRepository
import com.raulastete.lazypizza.domain.repository.MenuRepository
import com.raulastete.lazypizza.presentation.ui.model.ToppingCardUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class PizzaDetailViewModel(
    private val menuRepository: MenuRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PizzaDetailUiState(isLoading = true))
    val uiState: StateFlow<PizzaDetailUiState> = _uiState.asStateFlow()

    private val _event = Channel<PizzaDetailEvent?>()
    val event = _event.receiveAsFlow()

    private val userId = "me"

    init {
        loadToppings()
    }

    fun loadToppings() {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            menuRepository.getToppings()
                .collectLatest { toppings ->
                    _uiState.update {
                        it.copy(
                            toppingCardUis = toppings.map { topping ->
                                ToppingCardUi(
                                    topping = topping,
                                    count = 0
                                )
                            },
                            isLoading = false
                        )
                    }
                }
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

    fun getTotalPrice(pizzaUnitPrice: Double): String {

        val toppingsPrice = getToppingsPrice()

        return String.format(Locale.US, "Add to Cart for $%.2f", pizzaUnitPrice + toppingsPrice)
    }

    private fun getToppingsPrice(): Double {
        return uiState.value.toppingCardUis.filter { it.isSelected }.sumOf {
            it.count * it.topping.unitPrice
        }
    }

    fun addPizzaToCart(product: Product) {
        viewModelScope.launch {
            val selectedToppingsMap = mutableMapOf<Topping, Int>()

            _uiState.value.toppingCardUis.filter { it.count > 0 }.forEach { toppingCardUi ->
                selectedToppingsMap.put(toppingCardUi.topping, toppingCardUi.count)
            }

            val result = cartRepository.addOrUpdatePizzaInCart(
                product = product,
                toppings = selectedToppingsMap,
                userId = userId
            )

            if(result){
                _event.send(PizzaDetailEvent.OnPizzaAddedToCart)
            }
        }
    }
}

data class PizzaDetailUiState(
    val isLoading: Boolean = false,
    val toppingCardUis: List<ToppingCardUi> = emptyList(),
)

sealed interface PizzaDetailEvent {
    object OnPizzaAddedToCart : PizzaDetailEvent
}