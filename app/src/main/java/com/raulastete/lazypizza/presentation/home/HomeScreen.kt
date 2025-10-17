package com.raulastete.lazypizza.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raulastete.lazypizza.domain.entity.Category
import com.raulastete.lazypizza.presentation.home.components.CategoryListRow
import com.raulastete.lazypizza.presentation.home.components.CountableProductCard
import com.raulastete.lazypizza.presentation.home.components.PizzaCard
import com.raulastete.lazypizza.presentation.home.model.CountableProductUi
import com.raulastete.lazypizza.presentation.home.model.PizzaUi
import com.raulastete.lazypizza.presentation.home.model.ProductUi
import com.raulastete.lazypizza.ui.DeviceMode
import com.raulastete.lazypizza.ui.components.LPSearchBar
import com.raulastete.lazypizza.ui.components.LPTopbar
import com.raulastete.lazypizza.ui.theme.AppTheme
import com.raulastete.lazypizza.ui.theme.LocalDeviceMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.collections.component1
import kotlin.collections.component2

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel<HomeViewModel>(),
    navigateToPizzaDetail: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(
        uiState = uiState,
        onQuerySearch = viewModel::search,
        addGenericProductToCard = viewModel::addGenericProductToCard,
        removeGenericProductFromCard = viewModel::removeGenericProductFromCard,
        increaseGenericProductCount = viewModel::increaseGenericProductCount,
        decreaseGenericProductCount = viewModel::decreaseGenericProductCount,
        navigateToPizzaDetail = navigateToPizzaDetail
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onQuerySearch: (String) -> Unit,
    navigateToPizzaDetail: (String) -> Unit,
    addGenericProductToCard: (String) -> Unit,
    removeGenericProductFromCard: (String) -> Unit,
    increaseGenericProductCount: (String) -> Unit,
    decreaseGenericProductCount: (String) -> Unit

) {
    val lazyListState = rememberLazyListState()
    val lazyGridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    val deviceMode = LocalDeviceMode.current

    Column(
        Modifier
            .background(AppTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        LPTopbar(modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.height(8.dp))

        LPSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            query = uiState.searchQuery,
            onQueryChange = onQuerySearch
        )

        Spacer(Modifier.height(8.dp))

        CategoryListRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            categories = uiState.categoryNameList
        ) { categoryName ->
            navigateToStickyHeader(
                categoryName = categoryName,
                menu = uiState.data,
                lazyListState = lazyListState,
                lazyGridState = lazyGridState,
                coroutineScope = coroutineScope,
                deviceMode = deviceMode
            )
        }

        when {
            uiState.isLoading -> {
                ContentLoading()
            }

            uiState.showEmptyDataMessage -> {
                EmptyMessage()
            }

            else -> {
                when {
                    deviceMode is DeviceMode.PhonePortrait -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            state = lazyListState,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            ProductList(
                                uiState = uiState,
                                navigateToPizzaDetail = navigateToPizzaDetail,
                                addGenericProductToCard = addGenericProductToCard,
                                removeGenericProductFromCard = removeGenericProductFromCard,
                                increaseGenericProductCount = increaseGenericProductCount,
                                decreaseGenericProductCount = decreaseGenericProductCount
                            )
                        }
                    }

                    else -> {
                        LazyVerticalGrid(
                            modifier = Modifier.fillMaxSize(),
                            state = lazyGridState,
                            columns = Fixed(2),
                            contentPadding = PaddingValues(bottom = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            ProductList(
                                uiState = uiState,
                                navigateToPizzaDetail = navigateToPizzaDetail,
                                addGenericProductToCard = addGenericProductToCard,
                                removeGenericProductFromCard = removeGenericProductFromCard,
                                increaseGenericProductCount = increaseGenericProductCount,
                                decreaseGenericProductCount = decreaseGenericProductCount
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ContentLoading() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) { CircularProgressIndicator() }
}

@Composable
private fun EmptyMessage() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "No results. Try something else.",
            style = AppTheme.typography.title3,
            color = AppTheme.colorScheme.textSecondary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ProductItem(
    modifier: Modifier = Modifier,
    product: ProductUi,
    navigateToPizzaDetail: (String) -> Unit,
    addGenericProductToCard: (String) -> Unit,
    removeGenericProductFromCard: (String) -> Unit,
    increaseGenericProductCount: (String) -> Unit,
    decreaseGenericProductCount: (String) -> Unit
) {
    when (product) {
        is PizzaUi -> PizzaCard(
            modifier = modifier,
            pizzaUi = product
        ) {
            navigateToPizzaDetail(product.id)
        }

        is CountableProductUi -> CountableProductCard(
            modifier = modifier,
            countableProductUi = product,
            onClickAddToCart = {
                addGenericProductToCard(product.id)
            },
            onClickDecreaseCount = {
                decreaseGenericProductCount(product.id)
            },
            onClickIncreaseCount = {
                increaseGenericProductCount(product.id)
            },
            onClickRemoveFromCart = {
                removeGenericProductFromCard(product.id)
            }
        )
    }
}

private fun LazyListScope.ProductList(
    uiState: HomeUiState,
    navigateToPizzaDetail: (String) -> Unit,
    addGenericProductToCard : (String) -> Unit,
    removeGenericProductFromCard : (String) -> Unit,
    increaseGenericProductCount : (String) -> Unit,
    decreaseGenericProductCount : (String) -> Unit
) {
    uiState.data.forEach { (category, productList) ->
        stickyHeader(key = category.id) {
            CategoryHeader(category.name)
        }

        items(
            items = productList,
            key = { it.id },
            contentType = {
                if (category.isPizza) {
                    PizzaUi::class
                } else {
                    CountableProductUi::class
                }
            }
        ) { product ->
            ProductItem(
                product = product,
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

private fun LazyGridScope.ProductList(
    uiState: HomeUiState,
    navigateToPizzaDetail: (String) -> Unit,
    addGenericProductToCard : (String) -> Unit,
    removeGenericProductFromCard : (String) -> Unit,
    increaseGenericProductCount : (String) -> Unit,
    decreaseGenericProductCount : (String) -> Unit
) {
    uiState.data.forEach { (category, productList) ->
        stickyHeader(key = category.id) {
            CategoryHeader(category.name)
        }

        itemsIndexed(
            items = productList,
            key = { index, item -> item.id },
            contentType = { index, item ->
                if (category.isPizza) {
                    PizzaUi::class
                } else {
                    CountableProductUi::class
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

@Composable
private fun CategoryHeader(name: String) {
    Text(
        text = name,
        style = AppTheme.typography.label2Semibold,
        color = AppTheme.colorScheme.textSecondary,
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colorScheme.background)
            .padding(vertical = 12.dp)
            .padding(start = 16.dp)

    )
}

private fun navigateToStickyHeader(
    categoryName: String,
    menu: Map<Category, List<ProductUi>>,
    deviceMode: DeviceMode,
    lazyListState: LazyListState,
    lazyGridState: LazyGridState,
    coroutineScope: CoroutineScope
) {
    val headerIndex = menu.keys.indexOfFirst { it.name == categoryName.uppercase() }
    if (headerIndex == -1) return

    var absoluteIndex = 0
    menu.entries.take(headerIndex).forEach { entry ->
        absoluteIndex += 1 // 1 por el header
        absoluteIndex += entry.value.size // N por los ítems
    }

    coroutineScope.launch {
        when (deviceMode) {
            DeviceMode.PhonePortrait, DeviceMode.PhoneLandscape -> {
                lazyListState.animateScrollToItem(absoluteIndex)
            }

            DeviceMode.TabletPortrait, DeviceMode.TabletLandscape -> {
                lazyGridState.animateScrollToItem(absoluteIndex)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenContentPreview() {
    AppTheme {
        HomeScreenContent(
            uiState = HomeUiState(),
            onQuerySearch = {},
            navigateToPizzaDetail = {},
            addGenericProductToCard = {},
            removeGenericProductFromCard = {},
            increaseGenericProductCount = {},
            decreaseGenericProductCount = {}
        )
    }
}