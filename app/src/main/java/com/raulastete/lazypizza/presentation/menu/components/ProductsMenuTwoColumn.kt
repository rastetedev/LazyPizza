package com.raulastete.lazypizza.presentation.menu.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.presentation.menu.MenuUiState
import com.raulastete.lazypizza.presentation.ui.model.ProductCard

@Composable
fun ProductsMenuTwoColumn(
    lazyGridState: LazyGridState,
    uiState: MenuUiState,
    navigateToPizzaDetail: (String) -> Unit,
    addGenericProductToCard: (String) -> Unit,
    removeGenericProductFromCard: (String) -> Unit,
    increaseGenericProductCount: (String) -> Unit,
    decreaseGenericProductCount: (String) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        state = lazyGridState,
        columns = Fixed(2),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        uiState.menuByCategory.forEach { (category, productList) ->
            stickyHeader(key = category.id) {
                CategoryHeader(category.name)
            }

            itemsIndexed(
                items = productList,
                key = { index, item -> item.id },
                contentType = { index, item ->
                    if (category.isPizza) {
                        ProductCard.PizzaCard::class
                    } else {
                        ProductCard.GenericProductCard::class
                    }
                }
            ) { index, product ->
                ProductItem(
                    product = product,
                    navigateToPizzaDetail = navigateToPizzaDetail,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = if (index % 2 == 0) 16.dp else 0.dp,
                            end = if (index % 2 == 0) 0.dp else 16.dp
                        ),
                    addGenericProductToCard = addGenericProductToCard,
                    removeGenericProductFromCard = removeGenericProductFromCard,
                    increaseGenericProductCount = increaseGenericProductCount,
                    decreaseGenericProductCount = decreaseGenericProductCount
                )
            }
        }
    }
}