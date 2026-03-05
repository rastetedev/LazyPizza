package com.raulastete.lazypizza.presentation.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.presentation.model.ComplementFoodUi
import com.raulastete.lazypizza.presentation.model.PizzaUi
import com.raulastete.lazypizza.presentation.model.ProductUi
import com.raulastete.lazypizza.presentation.component.PrimaryIconButton
import com.raulastete.lazypizza.presentation.screens.home.component.ComplementFoodCard
import com.raulastete.lazypizza.presentation.screens.home.component.PizzaCard
import com.raulastete.lazypizza.presentation.theme.Outline
import com.raulastete.lazypizza.presentation.theme.Outline50
import com.raulastete.lazypizza.presentation.theme.body1Regular
import com.raulastete.lazypizza.presentation.theme.body3Bold
import com.raulastete.lazypizza.presentation.theme.body3Medium
import com.raulastete.lazypizza.presentation.theme.label2Semibold
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    onNavigateToPizzaDetail: (String) -> Unit,
    onNavigateToCart: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(
        uiState = uiState,
        onAction = { action ->
            when (action) {
                is HomeAction.OnClickPizzaCard -> {
                    onNavigateToPizzaDetail(action.pizzaId)
                }

                is HomeAction.OnClickLogoutButton -> {
                    //TODO: Replace when Auth is implemented
                    onNavigateToCart()
                }

                else -> {
                    viewModel.onAction(action)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onAction: (HomeAction) -> Unit
) {
    Scaffold(
        topBar = {
            HomeTopBar(onAction = onAction)
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                query = uiState.searchQuery,
                onAction = onAction
            )
            CategoryChips(categories = uiState.categories)
            ProductsByCategory(
                productsByCategory = uiState.productsByCategory,
                onAction = onAction
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopBar(onAction: (HomeAction) -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = { Title() },
        actions = {
            PhoneNumberContact()
            Spacer(modifier = Modifier.width(12.dp))
            LogoutButton(onAction = onAction)
            Spacer(modifier = Modifier.width(12.dp))
        }
    )
}

@Composable
private fun Title() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Image(
            modifier = Modifier.size(20.dp),
            imageVector = ImageVector.vectorResource(R.drawable.app_logo),
            contentDescription = null
        )
        Text("LazyPizza", style = MaterialTheme.typography.body3Bold)
    }
}

@Composable
private fun PhoneNumberContact() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.phone_icon),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            contentDescription = null,
        )
        Text(
            text = "+1 (555) 321-7890",
            style = MaterialTheme.typography.body1Regular.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}

@Composable
private fun LogoutButton(onAction: (HomeAction) -> Unit) {
    PrimaryIconButton(
        modifier = Modifier.size(32.dp),
        onClick = { onAction(HomeAction.OnClickLogoutButton) },
        icon = {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = ImageVector.vectorResource(R.drawable.logout_icon),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
        }
    )
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onAction: (HomeAction) -> Unit
) {
    Box(
        modifier
            .padding(horizontal = 16.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = CircleShape
            )
            .border(width = 1.dp, color = Outline50, shape = CircleShape)
            .padding(horizontal = 16.dp)
            .height(48.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.search_icon),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
            BasicTextField(
                modifier = Modifier.weight(1f),
                value = query,
                onValueChange = { onAction(HomeAction.OnQueryProducts(it)) },
                decorationBox = { innerTextField ->
                    if (query.isBlank()) {
                        Text(
                            text = "Search for delicious food...",
                            style = MaterialTheme.typography.body1Regular.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}

@Composable
private fun CategoryChips(
    categories: List<String>
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {

        itemsIndexed(categories) { index, category ->
            AssistChip(
                onClick = {},
                label = {
                    Text(
                        text = category,
                        style = MaterialTheme.typography.body3Medium
                    )
                },
                border = BorderStroke(width = 1.dp, color = Outline),
                shape = RoundedCornerShape(8.dp),
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                )
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProductsByCategory(
    productsByCategory: Map<String, List<ProductUi>>,
    onAction: (HomeAction) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        productsByCategory.forEach { (categoryName, products) ->
            stickyHeader(key = categoryName) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.background)
                        .padding(vertical = 8.dp),
                    text = categoryName,
                    style = MaterialTheme.typography.label2Semibold.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            items(
                items = products,
                key = { it.id },
                contentType = { it.type }
            ) { product ->
                when (product) {
                    is PizzaUi -> {
                        PizzaCard(
                            modifier = Modifier.fillMaxWidth(),
                            pizza = product,
                            onClick = { onAction(HomeAction.OnClickPizzaCard(product.id)) }
                        )
                    }

                    is ComplementFoodUi -> {
                        ComplementFoodCard(
                            modifier = Modifier.fillMaxWidth(),
                            complementFood = product,
                            onClickAddToCartButton = {
                                onAction(HomeAction.OnAddToCartComplementFoodCard(product.id))
                            },
                            onClickDeleteFromCartButton = {
                                onAction(HomeAction.OnRemoveFromCartComplementFood(product.id))
                            },
                            onClickDecreaseCountButton = {
                                onAction(HomeAction.OnDecreaseQuantityComplementFood(product.id))
                            },
                            onClickIncreaseCountButton = {
                                onAction(HomeAction.OnIncrementQuantityComplementFood(product.id))
                            }
                        )
                    }
                }
            }
        }
    }
}
