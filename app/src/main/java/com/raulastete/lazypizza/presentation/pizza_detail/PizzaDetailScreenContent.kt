package com.raulastete.lazypizza.presentation.pizza_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.raulastete.lazypizza.presentation.ui.components.designsystem.LPPrimaryButton
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@Composable
fun PhonePortraitMode(
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
                    pizzaName = pizzaProduct.name,
                    pizzaDescription = pizzaProduct.description
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabletPortraitMode(
    uiState: PizzaDetailUiState,
    pizzaProduct: Product,
    navigateBack: () -> Unit,
    onSelectTopping: (toppingId: String) -> Unit,
    onIncreaseToppingQuantity: (toppingId: String) -> Unit,
    onDecreaseToppingQuantity: (toppingId: String) -> Unit,
    onTotalPrice: () -> String,
    addPizzaToCart: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    BackButton(onClick = navigateBack)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppTheme.colorScheme.background,
                )
            )
        }, containerColor = AppTheme.colorScheme.surfaceHigher
    ) { paddingValues ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                Modifier
                    .background(AppTheme.colorScheme.background)
            ) {
                PizzaImage(
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
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
                    pizzaName = pizzaProduct.name,
                    pizzaDescription = pizzaProduct.description
                )
            }
            ToppingsSection(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(16.dp),
                gridCells = GridCells.Adaptive(minSize = 124.dp),
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandedWidthMode(
    uiState: PizzaDetailUiState,
    pizzaProduct: Product,
    navigateBack: () -> Unit,
    onTotalPrice: () -> String,
    onSelectTopping: (toppingId: String) -> Unit,
    onIncreaseToppingQuantity: (toppingId: String) -> Unit,
    onDecreaseToppingQuantity: (toppingId: String) -> Unit,
    addPizzaToCart: () -> Unit
) {
    Scaffold(
        containerColor = AppTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    BackButton(onClick = navigateBack)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppTheme.colorScheme.background,
                )
            )
        }
    ) { paddingValues ->
        Row(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = AppTheme.colorScheme.background)
        ) {
            Column(
                Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                PizzaImage(
                    modifier = Modifier.heightIn(max = 240.dp).align(Alignment.CenterHorizontally),
                    imageUrl = pizzaProduct.imageUrl
                )
                Spacer(Modifier.height(20.dp))
                PizzaInfo(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    pizzaName = pizzaProduct.name,
                    pizzaDescription = pizzaProduct.description
                )
            }
            Box(
                Modifier
                    .width(420.dp)
                    .background(
                        color = AppTheme.colorScheme.surfaceHigher,
                        shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                    )
                    .shadow(
                        elevation = 1.dp,
                        shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                    )
                    .padding(16.dp),
            ) {
                ToppingsSection(
                    toppings = uiState.toppingCardUis,
                    listPadding = PaddingValues(bottom = 80.dp),
                    onSelectTopping = onSelectTopping,
                    onIncreaseToppingQuantity = onIncreaseToppingQuantity,
                    onDecreaseToppingQuantity = onDecreaseToppingQuantity
                )
                LPPrimaryButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    text = onTotalPrice(),
                    onClick = addPizzaToCart
                )
            }
        }
    }
}