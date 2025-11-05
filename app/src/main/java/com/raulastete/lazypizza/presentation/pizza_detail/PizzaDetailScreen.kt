package com.raulastete.lazypizza.presentation.pizza_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raulastete.lazypizza.domain.entity.Product
import com.raulastete.lazypizza.domain.entity.Topping
import com.raulastete.lazypizza.presentation.ui.DeviceMode
import com.raulastete.lazypizza.presentation.ui.ObserveAsEvents
import com.raulastete.lazypizza.presentation.ui.model.ToppingCardUi
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme
import com.raulastete.lazypizza.presentation.ui.theme.LocalDeviceMode
import org.koin.androidx.compose.koinViewModel

@Composable
fun PizzaDetailScreen(
    pizzaProduct: Product,
    viewModel: PizzaDetailViewModel = koinViewModel<PizzaDetailViewModel>(),
    navigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val hapticFeedback = LocalHapticFeedback.current

    ObserveAsEvents(viewModel.event) {
        when (it) {
            PizzaDetailEvent.OnPizzaAddedToCart -> {
                hapticFeedback.performHapticFeedback(HapticFeedbackType.Confirm)
                navigateBack()
            }

            else -> {}
        }
    }

    Scaffold { paddingValues ->
        PizzaDetailScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            uiState = uiState,
            pizzaProduct = pizzaProduct,
            navigateBack = navigateBack,
            onSelectTopping = viewModel::selectTopping,
            onIncreaseToppingQuantity = viewModel::increaseToppingQuantity,
            onDecreaseToppingQuantity = viewModel::decreaseToppingQuantity,
            onTotalPrice = { viewModel.getTotalPrice(pizzaProduct.unitPrice) },
            addPizzaToCart = { viewModel.addPizzaToCart(pizzaProduct) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PizzaDetailScreen(
    modifier: Modifier = Modifier,
    uiState: PizzaDetailUiState,
    navigateBack: () -> Unit,
    pizzaProduct: Product,
    onSelectTopping: (toppingId: String) -> Unit,
    onIncreaseToppingQuantity: (toppingId: String) -> Unit,
    onDecreaseToppingQuantity: (toppingId: String) -> Unit,
    onTotalPrice: () -> String,
    addPizzaToCart: () -> Unit
) {

    val deviceMode = LocalDeviceMode.current

    if (uiState.isLoading) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    when (deviceMode) {
        DeviceMode.PhoneLandscape, DeviceMode.TabletLandscape -> {
            TwoColumnMode(
                modifier = modifier,
                uiState = uiState,
                pizzaProduct = pizzaProduct,
                navigateBack = navigateBack,
                onSelectTopping = onSelectTopping,
                onIncreaseToppingQuantity = onIncreaseToppingQuantity,
                onDecreaseToppingQuantity = onDecreaseToppingQuantity,
                onTotalPrice = onTotalPrice,
                addPizzaToCart = addPizzaToCart
            )
        }

        DeviceMode.PhonePortrait -> SingleColumnPhoneMode(
            modifier = modifier,
            uiState = uiState,
            pizzaProduct = pizzaProduct,
            navigateBack = navigateBack,
            onSelectTopping = onSelectTopping,
            onIncreaseToppingQuantity = onIncreaseToppingQuantity,
            onDecreaseToppingQuantity = onDecreaseToppingQuantity,
            addPizzaToCart = addPizzaToCart,
            onTotalPrice = onTotalPrice
        )


        DeviceMode.TabletPortrait -> SingleColumnTabletMode(
            modifier = modifier,
            uiState = uiState,
            pizzaProduct = pizzaProduct,
            navigateBack = navigateBack,
            onSelectTopping = onSelectTopping,
            onIncreaseToppingQuantity = onIncreaseToppingQuantity,
            onDecreaseToppingQuantity = onDecreaseToppingQuantity,
            addPizzaToCart = addPizzaToCart,
            onTotalPrice = onTotalPrice
        )
    }
}


private val samplePizza = Product(
    id = "1",
    name = "Pizza Margarita",
    description = "La pizza margarita es una pizza típica de Nápoles (Italia), cuyos ingredientes son tomate, mozzarella, albahaca fresca, sal y aceite. Es la pizza napolitana más popular, junto con la marinera.",
    unitPrice = 8.99,
    imageUrl = "",
    categoryId = "PIZZA"
)

private val sampleToppings = listOf(
    Topping(id = "t1", name = "Extra Queso", unitPrice = 1.50, imageUrl = ""),
    Topping(id = "t2", name = "Champiñones", unitPrice = 0.75, imageUrl = ""),
    Topping(id = "t3", name = "Peperoni", unitPrice = 1.25, imageUrl = ""),
    Topping(id = "t4", name = "Extra Queso", unitPrice = 1.50, imageUrl = ""),
    Topping(id = "t5", name = "Champiñones", unitPrice = 0.75, imageUrl = ""),
    Topping(id = "t6", name = "Peperoni", unitPrice = 1.25, imageUrl = ""),
    Topping(id = "t7", name = "Extra Queso", unitPrice = 1.50, imageUrl = ""),
    Topping(id = "t8", name = "Champiñones", unitPrice = 0.75, imageUrl = ""),
    Topping(id = "t9", name = "Peperoni", unitPrice = 1.25, imageUrl = "")
)

private val sampleUiState = PizzaDetailUiState(
    isLoading = false,
    toppingCardUis = sampleToppings.map {
        ToppingCardUi(
            topping = it,
            count = if (it.id == "t2") 1 else 0
        )
    }
)

@Preview(name = "Phone Portrait", showBackground = true, widthDp = 360, heightDp = 780)
@Composable
private fun PizzaDetailScreenPreview_SingleColumnPhone() {

    CompositionLocalProvider(
        LocalDeviceMode provides DeviceMode.PhonePortrait,
    ) {
        AppTheme {
            PizzaDetailScreen(
                uiState = sampleUiState,
                navigateBack = {},
                pizzaProduct = samplePizza,
                onSelectTopping = {},
                onIncreaseToppingQuantity = {},
                onDecreaseToppingQuantity = {},
                onTotalPrice = { "Add to Cart for $100.00" },
                addPizzaToCart = {}
            )
        }
    }
}

@Preview(name = "Phone Portrait", showBackground = true, widthDp = 780, heightDp = 360)
@Composable
private fun PizzaDetailScreenPreview_TwoColumnPhone() {

    CompositionLocalProvider(
        LocalDeviceMode provides DeviceMode.PhoneLandscape,
    ) {
        AppTheme {
            PizzaDetailScreen(
                uiState = sampleUiState,
                navigateBack = {},
                pizzaProduct = samplePizza,
                onSelectTopping = {},
                onIncreaseToppingQuantity = {},
                onDecreaseToppingQuantity = {},
                onTotalPrice = { "Add to Cart for $100.00" },
                addPizzaToCart = {}
            )
        }
    }
}

@Preview(name = "Tablet Portrait", showBackground = true, widthDp = 800, heightDp = 1200)
@Composable
private fun PizzaDetailScreenPreview_SingleColumnTablet() {

    CompositionLocalProvider(
        LocalDeviceMode provides DeviceMode.TabletPortrait,
    ) {
        AppTheme {
            PizzaDetailScreen(
                uiState = sampleUiState,
                navigateBack = {},
                pizzaProduct = samplePizza,
                onSelectTopping = {},
                onIncreaseToppingQuantity = {},
                onDecreaseToppingQuantity = {},
                onTotalPrice = { "Add to Cart for $100.00" },
                addPizzaToCart = {}
            )
        }
    }
}

@Preview(name = "Tablet Landscape", showBackground = true, widthDp = 1200, heightDp = 800)
@Composable
private fun PizzaDetailScreenPreview_TwoColumnTablet() {

    CompositionLocalProvider(
        LocalDeviceMode provides DeviceMode.TabletLandscape,
    ) {
        AppTheme {
            PizzaDetailScreen(
                uiState = sampleUiState,
                navigateBack = {},
                pizzaProduct = samplePizza,
                onSelectTopping = {},
                onIncreaseToppingQuantity = {},
                onDecreaseToppingQuantity = {},
                onTotalPrice = { "Add to Cart for $100.00" },
                addPizzaToCart = {}
            )
        }
    }
}