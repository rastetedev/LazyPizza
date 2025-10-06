package com.raulastete.lazypizza.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.raulastete.lazypizza.ui.components.LPSearchBar
import com.raulastete.lazypizza.ui.components.LPTopbar
import com.raulastete.lazypizza.ui.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel<HomeViewModel>(),
    navigateToPizzaDetail: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(
        uiState = uiState,
        onQuerySearch = viewModel::search,
        navigateToPizzaDetail = navigateToPizzaDetail
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onQuerySearch: (String) -> Unit,
    navigateToPizzaDetail: (String) -> Unit
) {
    val lazyListState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    Column(
        Modifier
            .background(AppTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        LPTopbar(
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        LPSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            query = uiState.searchQuery,
            onQueryChange = {
                onQuerySearch(it)
            }
        )

        Spacer(Modifier.height(8.dp))

        CategoryListRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            categories = uiState.categoryNameList
        ) {
            navigateToStickyHeader(
                categoryName = it,
                menu = uiState.data,
                lazyListState = lazyListState,
                coroutineScope = coroutineScope
            )
        }

        if (uiState.showEmptyDataMessage && uiState.isLoading.not()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    "No results. Try something else.",
                    style = AppTheme.typography.title3,
                    color = AppTheme.colorScheme.textSecondary,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState,
                verticalArrangement = Arrangement.spacedBy(8.dp),
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
                    ) {
                        if (category.isPizza) {
                            PizzaCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                pizzaUi = it as PizzaUi
                            ) {
                                navigateToPizzaDetail(it.id)
                            }
                        } else {
                            CountableProductCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                countableProductUi = it as CountableProductUi,
                                onClickAddToCart = {},
                                onClickDecreaseCount = {},
                                onClickIncreaseCount = {},
                                onClickRemoveFromCart = {}
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryHeader(name: String) {
    Text(
        text = name.uppercase(),
        style = AppTheme.typography.label2Semibold,
        color = AppTheme.colorScheme.textSecondary,
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colorScheme.background)
            .padding(vertical = 12.dp)
            .padding(start = 16.dp)

    )
}

fun navigateToStickyHeader(
    categoryName: String,
    menu: Map<Category, List<ProductUi>>,
    lazyListState: LazyListState,
    coroutineScope: CoroutineScope
) {
    val headerIndex = menu
        .keys
        .indexOfFirst { it.name == categoryName }
    if (headerIndex != -1) {
        var absoluteIndex = 0
        menu.entries.take(headerIndex)
            .forEach { entry ->
                absoluteIndex += 1 // header
                absoluteIndex += entry.value.size // items
            }

        coroutineScope.launch {
            lazyListState.animateScrollToItem(absoluteIndex)
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
            navigateToPizzaDetail = {}
        )
    }
}