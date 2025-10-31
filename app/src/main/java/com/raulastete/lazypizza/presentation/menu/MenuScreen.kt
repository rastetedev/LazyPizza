package com.raulastete.lazypizza.presentation.menu

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raulastete.lazypizza.domain.entity.Category
import com.raulastete.lazypizza.domain.entity.Product
import com.raulastete.lazypizza.presentation.menu.components.Banner
import com.raulastete.lazypizza.presentation.menu.components.CategoryListRow
import com.raulastete.lazypizza.presentation.menu.components.MenuItems
import com.raulastete.lazypizza.presentation.menu.components.MenuSkeleton
import com.raulastete.lazypizza.presentation.ui.DeviceMode
import com.raulastete.lazypizza.presentation.ui.components.LPSearchBar
import com.raulastete.lazypizza.presentation.ui.components.LPTopbar
import com.raulastete.lazypizza.presentation.ui.model.MenuCardUi
import com.raulastete.lazypizza.presentation.ui.model.PizzaCardUi
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme
import com.raulastete.lazypizza.presentation.ui.theme.LocalDeviceMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun MenuScreen(
    viewModel: MenuViewModel = koinViewModel<MenuViewModel>(),
    navigateToPizzaDetail: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MenuScreenContent(
        uiState = uiState,
        searchProduct = viewModel::searchProduct,
        addGenericProductToCard = viewModel::addGenericProductToCart,
        removeGenericProductFromCard = viewModel::removeGenericProductFromCart,
        increaseGenericProductCount = viewModel::increaseGenericProductCount,
        decreaseGenericProductCount = viewModel::decreaseGenericProductCount,
        navigateToPizzaDetail = navigateToPizzaDetail
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MenuScreenContent(
    uiState: MenuUiState,
    searchProduct: (productName: String) -> Unit,
    navigateToPizzaDetail: (pizzaId: String) -> Unit,
    addGenericProductToCard: (product: Product) -> Unit,
    removeGenericProductFromCard: (genericProductId: String) -> Unit,
    increaseGenericProductCount: (genericProductId: String, count: Int) -> Unit,
    decreaseGenericProductCount: (genericProductId: String, count: Int) -> Unit

) {
    when {
        uiState.isLoading -> {
            MenuSkeleton()
        }

        else -> {
            MenuContent(
                uiState = uiState,
                searchProduct = searchProduct,
                navigateToPizzaDetail = navigateToPizzaDetail,
                addGenericProductToCard = addGenericProductToCard,
                removeGenericProductFromCard = removeGenericProductFromCard,
                increaseGenericProductCount = increaseGenericProductCount,
                decreaseGenericProductCount = decreaseGenericProductCount
            )
        }
    }
}

@Composable
private fun MenuContent(
    uiState: MenuUiState,
    searchProduct: (String) -> Unit,
    navigateToPizzaDetail: (String) -> Unit,
    addGenericProductToCard: (Product) -> Unit,
    removeGenericProductFromCard: (String) -> Unit,
    increaseGenericProductCount: (String, count: Int) -> Unit,
    decreaseGenericProductCount: (String, count: Int) -> Unit
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

        Banner(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(16.dp))

        LPSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            query = uiState.searchProductQuery,
            onQueryChange = searchProduct
        )

        Spacer(Modifier.height(8.dp))

        CategoryListRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            categories = uiState.categoryNameList
        ) { categoryName ->
            navigateToStickyHeader(
                coroutineScope = coroutineScope,
                categoryName = categoryName,
                menu = uiState.menuByCategory,
                lazyListState = lazyListState,
                lazyGridState = lazyGridState,
                deviceMode = deviceMode
            )
        }

        MenuItems(
            uiState = uiState,
            lazyListState = lazyListState,
            lazyGridState = lazyGridState,
            navigateToPizzaDetail = navigateToPizzaDetail,
            addGenericProductToCard = addGenericProductToCard,
            removeGenericProductFromCard = removeGenericProductFromCard,
            increaseGenericProductCount = increaseGenericProductCount,
            decreaseGenericProductCount = decreaseGenericProductCount,
        )
    }
}

private fun navigateToStickyHeader(
    categoryName: String,
    menu: Map<Category, List<MenuCardUi>>,
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
private fun MenuScreenContentPreview() {
    AppTheme {

        CompositionLocalProvider(
            LocalDeviceMode provides DeviceMode.PhonePortrait
        ) {
            MenuScreenContent(
                uiState = MenuUiState(
                    isLoading = false,
                    menuByCategory = mapOf(
                        Category(id = "PIZZA", name = "Pizza") to listOf(
                            PizzaCardUi(
                                product = Product(
                                    id = "1",
                                    name = "Pizza de Muzzarella",
                                    description = "Pizza con muzzarella",
                                    unitPrice = 10.0,
                                    imageUrl = "",
                                    categoryId = "",
                                    toppings = null
                                )
                            )
                        )
                    )
                ),
                searchProduct = {},
                navigateToPizzaDetail = {},
                addGenericProductToCard = {},
                removeGenericProductFromCard = {},
                increaseGenericProductCount = { _, _ -> },
                decreaseGenericProductCount = { _, _ -> }
            )
        }

    }
}