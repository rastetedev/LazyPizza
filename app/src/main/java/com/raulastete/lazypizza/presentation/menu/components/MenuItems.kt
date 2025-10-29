package com.raulastete.lazypizza.presentation.menu.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.raulastete.lazypizza.presentation.menu.MenuUiState
import com.raulastete.lazypizza.presentation.ui.DeviceMode
import com.raulastete.lazypizza.presentation.ui.theme.LocalDeviceMode

@Composable
fun MenuItems(
    uiState: MenuUiState,
    lazyListState: LazyListState,
    lazyGridState: LazyGridState,
    navigateToPizzaDetail: (String) -> Unit,
    addGenericProductToCard: (String) -> Unit,
    removeGenericProductFromCard: (String) -> Unit,
    increaseGenericProductCount: (String) -> Unit,
    decreaseGenericProductCount: (String) -> Unit
){
    val deviceMode = LocalDeviceMode.current

    when {
        uiState.showEmptyResultsForQuery -> {
            EmptyMessage(modifier = Modifier.fillMaxSize())
        }

        deviceMode is DeviceMode.PhonePortrait -> CategorizedProductList(
            lazyListState = lazyListState,
            uiState = uiState,
            navigateToPizzaDetail = navigateToPizzaDetail,
            addGenericProductToCard = addGenericProductToCard,
            removeGenericProductFromCard = removeGenericProductFromCard,
            increaseGenericProductCount = increaseGenericProductCount,
            decreaseGenericProductCount = decreaseGenericProductCount
        )

        else -> CategorizedProductGrid(
            lazyGridState = lazyGridState,
            uiState = uiState,
            navigateToPizzaDetail = navigateToPizzaDetail,
            addGenericProductToCard = addGenericProductToCard,
            removeGenericProductFromCard = removeGenericProductFromCard,
            increaseGenericProductCount = increaseGenericProductCount,
            decreaseGenericProductCount = decreaseGenericProductCount
        )
    }
}