package com.raulastete.lazypizza.presentation.pizza_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.raulastete.lazypizza.NavRoute
import com.raulastete.lazypizza.domain.CartItem
import com.raulastete.lazypizza.domain.CartRepository
import com.raulastete.lazypizza.domain.Extra
import com.raulastete.lazypizza.domain.Product
import com.raulastete.lazypizza.domain.ProductRepository
import com.raulastete.lazypizza.presentation.model.ToppingUi
import com.raulastete.lazypizza.ui.util.formatCurrency
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.math.BigDecimal

class PizzaDetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val route: NavRoute.PizzaDetail = savedStateHandle.toRoute()
    private val productId: String = route.productId

    private val _toppingCounts = MutableStateFlow<Map<String, Int>>(emptyMap())
    private val _isLoading = MutableStateFlow(true)
    private val _pizza = MutableStateFlow<Product?>(null)

    init {
        viewModelScope.launch {
            _pizza.value = productRepository.getProductById(productId)
            _isLoading.value = false
        }
    }

    val uiState: StateFlow<PizzaDetailUiState> = combine(
        _pizza,
        productRepository.getToppings(),
        _toppingCounts,
        _isLoading
    ) { pizza, toppings, counts, isLoading ->

        // Calcular precio de la pizza base
        val pizzaBasePrice = pizza?.unitPrice ?: BigDecimal.ZERO

        // Calcular suma de precios de toppings seleccionados
        val toppingsTotalPrice = toppings.sumOf { topping ->
            val count = counts[topping.id] ?: 0
            topping.unitPrice.multiply(BigDecimal(count))
        }

        // Precio total final
        val totalAmount = pizzaBasePrice.add(toppingsTotalPrice)

        PizzaDetailUiState(
            pizzaName = pizza?.name ?: "",
            ingredientsText = pizza?.description ?: "",
            pizzaImage = pizza?.imageUrl ?: "",
            toppings = toppings.map { topping ->
                ToppingUi(
                    id = topping.id,
                    name = topping.name,
                    image = topping.imageUrl,
                    unitPrice = topping.unitPrice.formatCurrency(),
                    count = counts[topping.id] ?: 0
                )
            },
            totalPrice = totalAmount.formatCurrency(),
            isLoading = isLoading
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PizzaDetailUiState()
        )

    fun onAction(action: PizzaDetailAction) {
        when (action) {
            is PizzaDetailAction.OnIncrementTopping -> {
                _toppingCounts.update { it + (action.toppingId to (it[action.toppingId] ?: 0) + 1) }
            }

            is PizzaDetailAction.OnDecreaseTopping -> {
                _toppingCounts.update { current ->
                    val count = current[action.toppingId] ?: 0
                    if (count > 0) current + (action.toppingId to count - 1) else current
                }
            }

            PizzaDetailAction.OnClickAddToCart -> {
                viewModelScope.launch {
                    val pizza = _pizza.value ?: return@launch
                    val allToppings = productRepository.getToppings().first()
                    
                    // Mapeamos los toppings para enviar solo el formato "cantidad x nombre"
                    val selectedToppings = allToppings.mapNotNull { topping ->
                        val count = _toppingCounts.value[topping.id] ?: 0
                        if (count > 0) Extra(
                            unitPrice = topping.unitPrice,
                            name = topping.name
                        ) to count else null
                    }.toMap()

                    cartRepository.addOrUpdateItem(
                        CartItem(
                            id = pizza.id,
                            product = pizza,
                            quantity = 1,
                            extras = selectedToppings
                        )
                    )
                }
            }

            else -> Unit
        }
    }
}
