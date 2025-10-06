package com.raulastete.lazypizza.presentation.pizza_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.presentation.home.model.PizzaUi
import com.raulastete.lazypizza.presentation.pizza_detail.components.ToppingCard
import com.raulastete.lazypizza.presentation.pizza_detail.model.ToppingUi
import com.raulastete.lazypizza.ui.DeviceMode
import com.raulastete.lazypizza.ui.components.FadingEdgeVerticalList
import com.raulastete.lazypizza.ui.components.LPPrimaryButton
import com.raulastete.lazypizza.ui.theme.AppTheme
import com.raulastete.lazypizza.ui.theme.LocalDeviceMode
import org.koin.androidx.compose.koinViewModel

@Composable
fun PizzaDetailScreen(
    viewModel: PizzaDetailViewModel = koinViewModel<PizzaDetailViewModel>(),
    navigateBack: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ProductDetailScreenContent(
        uiState = uiState,
        navigateBack = navigateBack,
        onSelectTopping = viewModel::selectTopping,
        onIncreaseToppingQuantity = viewModel::increaseToppingQuantity,
        onDecreaseToppingQuantity = viewModel::decreaseToppingQuantity,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductDetailScreenContent(
    uiState: PizzaDetailUiState,
    navigateBack: () -> Unit,
    onSelectTopping: (String) -> Unit,
    onIncreaseToppingQuantity: (String) -> Unit,
    onDecreaseToppingQuantity: (String) -> Unit,
) {

    val deviceMode = LocalDeviceMode.current

    if (uiState.isLoading) {
        Box(
            Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        if (deviceMode == DeviceMode.PhonePortrait || deviceMode == DeviceMode.TabletLandscape) {
            SmallWidthMode(
                uiState = uiState,
                navigateBack = navigateBack,
                onSelectTopping = onSelectTopping,
                onIncreaseToppingQuantity = onIncreaseToppingQuantity,
                onDecreaseToppingQuantity = onDecreaseToppingQuantity
            )
        } else {
            TwoColumnMode(
                uiState = uiState,
                navigateBack = navigateBack,
                onSelectTopping = onSelectTopping,
                onIncreaseToppingQuantity = onIncreaseToppingQuantity,
                onDecreaseToppingQuantity = onDecreaseToppingQuantity
            )
        }
    }
}

@Composable
private fun SmallWidthMode(
    uiState: PizzaDetailUiState,
    navigateBack: () -> Unit,
    onSelectTopping: (String) -> Unit,
    onIncreaseToppingQuantity: (String) -> Unit,
    onDecreaseToppingQuantity: (String) -> Unit,
) {
    Box(Modifier.background(AppTheme.colorScheme.surfaceHigher)) {
        Column {
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
                    imageUrl = uiState.pizzaUi?.imageUrl
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
                    pizzaUi = uiState.pizzaUi
                )
            }
            ToppingSections(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                toppings = uiState.toppings,
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
            text = "Add to Cart for $${uiState.formattedTotalPrice}"
        ) { }
    }
}

@Composable
private fun TwoColumnMode(
    uiState: PizzaDetailUiState,
    navigateBack: () -> Unit,
    onSelectTopping: (String) -> Unit,
    onIncreaseToppingQuantity: (String) -> Unit,
    onDecreaseToppingQuantity: (String) -> Unit,
) {
    Column(
        Modifier
            .background(color = AppTheme.colorScheme.background),
    ) {
        BackButton(
            modifier = Modifier
                .statusBarsPadding()
                .padding(start = 10.dp, top = 8.dp),
            onClick = navigateBack
        )
        Spacer(Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .weight(1f),
            ) {
                Column {
                    PizzaImage(
                        modifier = Modifier.fillMaxWidth(),
                        imageUrl = uiState.pizzaUi?.imageUrl
                    )
                    Spacer(Modifier.height(20.dp))
                    PizzaInfo(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        pizzaUi = uiState.pizzaUi
                    )
                }
            }

            Column(
                Modifier
                    .weight(1f)
                    .background(
                        color = AppTheme.colorScheme.surfaceHigher,
                        shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                    ),
                verticalArrangement = Arrangement.Center
            ) {
                ToppingSections(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    toppings = uiState.toppings,
                    listPadding = PaddingValues(bottom = 16.dp),
                    onSelectTopping = onSelectTopping,
                    onIncreaseToppingQuantity = onIncreaseToppingQuantity,
                    onDecreaseToppingQuantity = onDecreaseToppingQuantity,
                )
                LPPrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 32.dp),
                    text = "Add to Cart for $${uiState.formattedTotalPrice}"
                ) { }
            }
        }
        Spacer(Modifier.weight(1f))
    }

}

@Composable
private fun BackButton(modifier: Modifier, onClick: () -> Unit) {
    FilledIconButton(
        modifier = modifier,
        onClick = onClick, colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = AppTheme.colorScheme.textSecondary8
        )
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left),
            contentDescription = null,
            tint = AppTheme.colorScheme.textSecondary
        )
    }
}

@Composable
private fun PizzaImage(modifier: Modifier = Modifier, imageUrl: String?) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier.size(240.dp),
            contentScale = ContentScale.FillHeight,
            alignment = Alignment.BottomCenter
        )
    }
}

@Composable
private fun PizzaInfo(modifier: Modifier, pizzaUi: PizzaUi?) {
    Column(
        modifier = modifier
    ) {
        Text(
            pizzaUi?.name.orEmpty(),
            style = AppTheme.typography.title1Semibold,
            color = AppTheme.colorScheme.textPrimary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(Modifier.height(4.dp))
        Text(
            pizzaUi?.description.orEmpty(),
            style = AppTheme.typography.body3Regular,
            color = AppTheme.colorScheme.textSecondary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
    }
}

@Composable
private fun ToppingSections(
    modifier: Modifier = Modifier,
    toppings: List<ToppingUi>,
    listPadding: PaddingValues,
    onSelectTopping: (String) -> Unit,
    onIncreaseToppingQuantity: (String) -> Unit,
    onDecreaseToppingQuantity: (String) -> Unit,
) {

    val lazyGridState = rememberLazyGridState()

    Column(
        modifier = modifier
    ) {
        Text(
            "ADD EXTRA TOPPINGS",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = AppTheme.typography.label2Semibold,
            color = AppTheme.colorScheme.textSecondary
        )
        Spacer(Modifier.height(12.dp))

        FadingEdgeVerticalList(
            listState = lazyGridState,
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                state = lazyGridState,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = listPadding
            ) {
                items(toppings) {
                    ToppingCard(
                        modifier = Modifier.weight(1f),
                        toppingUi = it,
                        onClick = {
                            onSelectTopping(it.id)
                        },
                        onClickIncreaseCount = {
                            onIncreaseToppingQuantity(it.id)
                        },
                        onClickDecreaseCount = {
                            onDecreaseToppingQuantity(it.id)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProductDetailScreenContentPreview() {
    AppTheme {
        ProductDetailScreenContent(
            uiState = PizzaDetailUiState(),
            navigateBack = {},
            onSelectTopping = {},
            onIncreaseToppingQuantity = {},
            onDecreaseToppingQuantity = {}
        )
    }
}