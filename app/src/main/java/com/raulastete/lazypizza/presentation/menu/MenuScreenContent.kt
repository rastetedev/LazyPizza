package com.raulastete.lazypizza.presentation.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.domain.entity.Category
import com.raulastete.lazypizza.domain.entity.Product
import com.raulastete.lazypizza.presentation.menu.components.Banner
import com.raulastete.lazypizza.presentation.menu.components.CategoryHeader
import com.raulastete.lazypizza.presentation.menu.components.CategoryListRow
import com.raulastete.lazypizza.presentation.menu.components.NoProductsFound
import com.raulastete.lazypizza.presentation.menu.components.MenuCard
import com.raulastete.lazypizza.presentation.ui.DeviceMode
import com.raulastete.lazypizza.presentation.ui.components.designsystem.LPSearchBar
import com.raulastete.lazypizza.presentation.ui.model.GenericProductCardUi
import com.raulastete.lazypizza.presentation.ui.model.MenuCardUi
import com.raulastete.lazypizza.presentation.ui.model.PizzaCardUi
import com.raulastete.lazypizza.presentation.ui.theme.LocalDeviceMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MenuScreenContent(
    modifier: Modifier,
    uiState: MenuUiState,
    navigateToPizzaDetail: (Product) -> Unit,
    addGenericProductToCard: (Product) -> Unit,
    searchProduct: (String) -> Unit,
    removeGenericProductFromCard: (String) -> Unit,
    increaseGenericProductCount: (String, Int) -> Unit,
    decreaseGenericProductCount: (String, Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val lazyGridState = rememberLazyGridState()

    val deviceMode = LocalDeviceMode.current

    val productItemGridSpan = remember(deviceMode) {
        if (deviceMode == DeviceMode.PhonePortrait) {
            GridItemSpan(2)
        } else {
            GridItemSpan(1)
        }
    }

    LazyVerticalGrid(
        modifier = modifier,
        state = lazyGridState,
        columns = Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item(span = { GridItemSpan(2) }) {
            Banner(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }

        item(span = { GridItemSpan(2) }) {
            LPSearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                query = uiState.searchProductQuery,
                onQueryChange = searchProduct
            )
        }

        if (uiState.showEmptyResultsForQuery) {
            item(span = { GridItemSpan(2) }) {
                NoProductsFound(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                )
            }
        } else {

            item(span = { GridItemSpan(2) }) {
                CategoryListRow(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    categories = uiState.categoryNameList
                ) { categoryName ->
                    navigateToStickyHeader(
                        coroutineScope = coroutineScope,
                        categoryName = categoryName,
                        menu = uiState.menuByCategory,
                        lazyGridState = lazyGridState
                    )
                }
            }

            uiState.menuByCategory.forEach { (category, menuCardUis) ->
                stickyHeader(key = category.id) {
                    CategoryHeader(
                        category.name
                    )
                }

                itemsIndexed(
                    items = menuCardUis,
                    key = { index, item -> item.product.id },
                    span = { index, item -> productItemGridSpan },
                    contentType = { index, item ->
                        if (category.isPizza) {
                            PizzaCardUi::class
                        } else {
                            GenericProductCardUi::class
                        }
                    },
                ) { index, menuCardUi ->
                    MenuCard(
                        menuCardUi = menuCardUi,
                        navigateToPizzaDetail = navigateToPizzaDetail,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                if (deviceMode == DeviceMode.PhonePortrait) {
                                    PaddingValues(horizontal = 16.dp)
                                } else {
                                    PaddingValues(
                                        start = if (index % 2 == 0) 16.dp else 0.dp,
                                        end = if (index % 2 == 0) 0.dp else 16.dp
                                    )
                                }
                            ),
                        addGenericProductToCard = addGenericProductToCard,
                        removeGenericProductFromCard = removeGenericProductFromCard,
                        increaseGenericProductCount = increaseGenericProductCount,
                        decreaseGenericProductCount = decreaseGenericProductCount
                    )
                }
            }

            item {
                Spacer(Modifier.height(32.dp))
            }
        }
    }
}

private fun navigateToStickyHeader(
    categoryName: String,
    menu: Map<Category, List<MenuCardUi>>,
    lazyGridState: LazyGridState,
    coroutineScope: CoroutineScope
) {
    val headerIndex = menu.keys.indexOfFirst { it.name == categoryName.uppercase() }
    if (headerIndex == -1) return

    var absoluteIndex = 3 // For banner, search bar, categories
    menu.entries.take(headerIndex).forEach { entry ->
        absoluteIndex += 1 // 1 por el header
        absoluteIndex += entry.value.size // N por los ítems
    }

    coroutineScope.launch {
        lazyGridState.animateScrollToItem(absoluteIndex)
    }
}