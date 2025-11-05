package com.raulastete.lazypizza.presentation.pizza_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.domain.entity.Product
import com.raulastete.lazypizza.presentation.pizza_detail.component.BackButton
import com.raulastete.lazypizza.presentation.pizza_detail.component.PizzaImage
import com.raulastete.lazypizza.presentation.pizza_detail.component.PizzaInfo
import com.raulastete.lazypizza.presentation.pizza_detail.component.ToppingsSection
import com.raulastete.lazypizza.presentation.ui.components.LPPrimaryButton
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@Composable
fun SingleColumnPhoneMode(
    modifier: Modifier,
    uiState: PizzaDetailUiState,
    pizzaProduct: Product,
    navigateBack: () -> Unit,
    onSelectTopping: (toppingId: String) -> Unit,
    onIncreaseToppingQuantity: (toppingId: String) -> Unit,
    onDecreaseToppingQuantity: (toppingId: String) -> Unit,
    addPizzaToCart: () -> Unit,
    onTotalPrice: () -> String
) {
    Box(modifier.background(AppTheme.colorScheme.surfaceHigher)) {
        Column {
            Column(
                Modifier
                    .background(AppTheme.colorScheme.background)
            ) {
                BackButton(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 24.dp),
                    onClick = navigateBack
                )
                PizzaImage(
                    modifier = Modifier.fillMaxWidth(),
                    imageUrl = pizzaProduct.imageUrl
                )
                PizzaInfo(
                    Modifier
                        .fillMaxWidth()
                        .background(
                            color = AppTheme.colorScheme.surfaceHigher,
                            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                        )
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .padding(16.dp),
                    product = pizzaProduct
                )
            }
            ToppingsSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                toppings = uiState.toppingCardUis,
                listPadding = PaddingValues(bottom = 64.dp),
                onSelectTopping = onSelectTopping,
                onIncreaseToppingQuantity = onIncreaseToppingQuantity,
                onDecreaseToppingQuantity = onDecreaseToppingQuantity,
            )
        }
        LPPrimaryButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp),
            text = onTotalPrice(),
            onClick = addPizzaToCart
        )
    }
}

@Composable
fun SingleColumnTabletMode(
    modifier: Modifier,
    uiState: PizzaDetailUiState,
    pizzaProduct: Product,
    navigateBack: () -> Unit,
    onSelectTopping: (toppingId: String) -> Unit,
    onIncreaseToppingQuantity: (toppingId: String) -> Unit,
    onDecreaseToppingQuantity: (toppingId: String) -> Unit,
    onTotalPrice: () -> String,
    addPizzaToCart: () -> Unit
) {

    Column(modifier.background(AppTheme.colorScheme.surfaceHigher)) {
        Column(
            Modifier
                .background(AppTheme.colorScheme.background)
        ) {
            BackButton(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(start = 10.dp, top = 8.dp),
                onClick = navigateBack
            )
            PizzaImage(
                modifier = Modifier.fillMaxWidth(),
                imageUrl = pizzaProduct.imageUrl
            )
            PizzaInfo(
                Modifier
                    .fillMaxWidth()
                    .background(
                        color = AppTheme.colorScheme.surfaceHigher,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .padding(16.dp),
                product = pizzaProduct
            )
        }
        ToppingsSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f),
            toppings = uiState.toppingCardUis,
            listPadding = PaddingValues(bottom = 64.dp),
            onSelectTopping = onSelectTopping,
            onIncreaseToppingQuantity = onIncreaseToppingQuantity,
            onDecreaseToppingQuantity = onDecreaseToppingQuantity,
        )
        LPPrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp),
            text = onTotalPrice(),
            onClick = addPizzaToCart
        )
    }
}

@Composable
fun TwoColumnMode(
    modifier : Modifier,
    uiState: PizzaDetailUiState,
    pizzaProduct: Product,
    navigateBack: () -> Unit,
    onTotalPrice: () -> String,
    onSelectTopping: (toppingId: String) -> Unit,
    onIncreaseToppingQuantity: (toppingId: String) -> Unit,
    onDecreaseToppingQuantity: (toppingId: String) -> Unit,
    addPizzaToCart: () -> Unit
) {
    Row(
        modifier
            .background(color = AppTheme.colorScheme.background)
    ) {
        Column(Modifier.weight(1f)) {
            BackButton(
                modifier = Modifier
                    .padding(start = 10.dp, top = 8.dp),
                onClick = navigateBack
            )
            PizzaImage(
                modifier = Modifier.fillMaxWidth(),
                imageUrl = pizzaProduct.imageUrl
            )
            Spacer(Modifier.height(20.dp))
            PizzaInfo(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                product = pizzaProduct
            )
        }
        Box(
            Modifier
                .weight(1f)
                .background(
                    color = AppTheme.colorScheme.surfaceHigher,
                    shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                )
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                ),
        ) {
            ToppingsSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                toppings = uiState.toppingCardUis,
                listPadding = PaddingValues(bottom = 64.dp),
                onSelectTopping = onSelectTopping,
                onIncreaseToppingQuantity = onIncreaseToppingQuantity,
                onDecreaseToppingQuantity = onDecreaseToppingQuantity
            )
            LPPrimaryButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 32.dp),
                text = onTotalPrice(),
                onClick = addPizzaToCart
            )
        }
    }
}