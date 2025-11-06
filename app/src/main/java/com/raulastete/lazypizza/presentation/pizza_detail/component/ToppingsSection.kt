package com.raulastete.lazypizza.presentation.pizza_detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.presentation.ui.components.FadingEdgeVerticalList
import com.raulastete.lazypizza.presentation.ui.model.ToppingCardUi
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@Composable
fun ToppingsSection(
    modifier: Modifier = Modifier,
    gridCells: GridCells = GridCells.Fixed(3),
    toppings: List<ToppingCardUi>,
    listPadding: PaddingValues,
    onSelectTopping: (toppingId: String) -> Unit,
    onIncreaseToppingQuantity: (toppingId: String) -> Unit,
    onDecreaseToppingQuantity: (toppingId: String) -> Unit
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
                columns = gridCells,
                state = lazyGridState,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = listPadding
            ) {
                items(toppings) {
                    ToppingCard(
                        modifier = Modifier
                            .size(width = 124.dp, height = 142.dp),
                        toppingCardUi = it,
                        onClick = {
                            onSelectTopping(it.topping.id)
                        },
                        onClickIncreaseCount = {
                            onIncreaseToppingQuantity(it.topping.id)
                        },
                        onClickDecreaseCount = {
                            onDecreaseToppingQuantity(it.topping.id)
                        }
                    )
                }
            }
        }
    }
}