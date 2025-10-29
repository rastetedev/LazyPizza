package com.raulastete.lazypizza.presentation.menu.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.presentation.menu.MenuUiState
import com.raulastete.lazypizza.presentation.ui.model.GenericProductCardUi
import com.raulastete.lazypizza.presentation.ui.model.PizzaCardUi

@Composable
fun CategorizedProductList(
    lazyListState: LazyListState,
    uiState: MenuUiState,
    navigateToPizzaDetail: (String) -> Unit,
    addGenericProductToCard: (String) -> Unit,
    removeGenericProductFromCard: (String) -> Unit,
    increaseGenericProductCount: (String) -> Unit,
    decreaseGenericProductCount: (String) -> Unit
){
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        uiState.menuByCategory.forEach { (category, menuCardUis) ->
            stickyHeader(key = category.id) {
                CategoryHeader(category.name)
            }

            items(
                items = menuCardUis,
                key = { it.id },
                contentType = {
                    if (category.isPizza) {
                        PizzaCardUi::class
                    } else {
                        GenericProductCardUi::class
                    }
                }
            ) { menuCardUi ->
                MenuCard(
                    menuCardUi = menuCardUi,
                    navigateToPizzaDetail = navigateToPizzaDetail,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    addGenericProductToCard = addGenericProductToCard,
                    removeGenericProductFromCard = removeGenericProductFromCard,
                    increaseGenericProductCount = increaseGenericProductCount,
                    decreaseGenericProductCount = decreaseGenericProductCount
                )
            }
        }
    }
}