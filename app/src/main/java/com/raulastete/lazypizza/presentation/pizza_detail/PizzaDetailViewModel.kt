package com.raulastete.lazypizza.presentation.pizza_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulastete.lazypizza.domain.MenuRepository
import com.raulastete.lazypizza.presentation.pizza_detail.model.ToppingUi
import com.raulastete.lazypizza.presentation.pizza_detail.model.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.map

class PizzaDetailViewModel(
    private val menuRepository: MenuRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PizzaDetailUiState>(PizzaDetailUiState())
    val uiState: StateFlow<PizzaDetailUiState> = _uiState.asStateFlow()

    init {
        fetchToppings()
    }

    private fun fetchToppings() {
        viewModelScope.launch {
            menuRepository.getToppings()
                .catch { exception ->
                    Log.e("Product Detail", exception.message, exception)
                }
                .collectLatest { data ->
                    _uiState.update { state ->
                        state.copy(
                            toppings = data.map { it.toUi() }
                        )
                    }
                }
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
    val toppings: List<ToppingUi> = emptyList()
)