package com.raulastete.lazypizza.presentation.pizza_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.presentation.pizza_detail.components.ToppingCard
import com.raulastete.lazypizza.presentation.pizza_detail.model.ToppingUi
import com.raulastete.lazypizza.ui.components.FadingEdgeVerticalList
import com.raulastete.lazypizza.ui.components.LPPrimaryButton
import com.raulastete.lazypizza.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun PizzaDetailScreen(
    viewModel: PizzaDetailViewModel = koinViewModel<PizzaDetailViewModel>()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ProductDetailScreenContent(
        uiState = uiState,
        onSelectTopping = viewModel::selectTopping,
        onIncreaseToppingQuantity = viewModel::increaseToppingQuantity,
        onDecreaseToppingQuantity = viewModel::decreaseToppingQuantity,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductDetailScreenContent(
    uiState: PizzaDetailUiState,
    onSelectTopping: (String) -> Unit,
    onIncreaseToppingQuantity: (String) -> Unit,
    onDecreaseToppingQuantity: (String) -> Unit,
) {
    Box(
        Modifier
            .fillMaxSize()
    ) {
        Column {
            Box {
                ProductHeader()
                BackButton(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                        .align(alignment = Alignment.TopStart),
                    onClick = {}
                )
            }
            ToppingSections(
                modifier = Modifier.weight(1f),
                toppings = uiState.toppings,
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
            text = "Add to Cart for $12.99"
        ) { }
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
private fun ProductHeader() {
    Column(Modifier.background(AppTheme.colorScheme.background)) {
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35f)
        ) {

        }
        Spacer(Modifier.height(20.dp))
        Column(
            Modifier
                .fillMaxWidth()
                .background(
                    color = AppTheme.colorScheme.surfaceHigher,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                "Margherita",
                style = AppTheme.typography.title1Semibold,
                color = AppTheme.colorScheme.textPrimary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "Tomato sauce, mozzarella, fresh basil, olive oil",
                style = AppTheme.typography.body3Regular,
                color = AppTheme.colorScheme.textSecondary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }

    }
}

@Composable
private fun ToppingSections(
    modifier: Modifier = Modifier,
    toppings: List<ToppingUi>,
    onSelectTopping: (String) -> Unit,
    onIncreaseToppingQuantity: (String) -> Unit,
    onDecreaseToppingQuantity: (String) -> Unit,
) {

    val lazyGridState = rememberLazyGridState()

    Column(
        modifier
            .fillMaxWidth()
            .background(color = AppTheme.colorScheme.surfaceHigher)
            .padding(16.dp)
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
            modifier = Modifier.fillMaxSize(),
            listState = lazyGridState,
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                state = lazyGridState,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 64.dp)
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
            onSelectTopping = {},
            onIncreaseToppingQuantity = {},
            onDecreaseToppingQuantity = {}
        )
    }
}