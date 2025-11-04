package com.raulastete.lazypizza.presentation.menu

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raulastete.lazypizza.domain.entity.Product
import com.raulastete.lazypizza.presentation.ui.components.topbar.LPTopbar
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun MenuScreen(
    viewModel: MenuViewModel = koinViewModel<MenuViewModel>(),
    navigateToPizzaDetail: (Product) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MenuScreen(
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
private fun MenuScreen(
    uiState: MenuUiState,
    searchProduct: (productName: String) -> Unit,
    navigateToPizzaDetail: (product: Product) -> Unit,
    addGenericProductToCard: (product: Product) -> Unit,
    removeGenericProductFromCard: (genericProductId: String) -> Unit,
    increaseGenericProductCount: (genericProductId: String, count: Int) -> Unit,
    decreaseGenericProductCount: (genericProductId: String, count: Int) -> Unit

) {
    Scaffold(
        topBar = { LPTopbar(modifier = Modifier.fillMaxWidth()) },
        containerColor = AppTheme.colorScheme.background
    ) { contentPadding ->

        when {
            uiState.isLoading -> {
                MenuScreenContentSkeleton(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                )
            }

            else -> {
                MenuScreenContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding),
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
}