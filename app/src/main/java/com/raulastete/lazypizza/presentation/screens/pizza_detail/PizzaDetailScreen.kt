package com.raulastete.lazypizza.presentation.screens.pizza_detail

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.presentation.model.ToppingUi
import com.raulastete.lazypizza.presentation.component.PrimaryButton
import com.raulastete.lazypizza.presentation.component.PrimaryIconButton
import com.raulastete.lazypizza.presentation.component.SkeletonBox
import com.raulastete.lazypizza.presentation.screens.pizza_detail.component.TopicCard
import com.raulastete.lazypizza.presentation.theme.TextSecondary8
import com.raulastete.lazypizza.presentation.theme.body3Regular
import com.raulastete.lazypizza.presentation.theme.label2Semibold
import com.raulastete.lazypizza.presentation.theme.title1Semibold
import com.raulastete.lazypizza.presentation.util.bottomFadingEdge
import org.koin.androidx.compose.koinViewModel

@Composable
fun PizzaDetailScreen(
    viewModel: PizzaDetailViewModel = koinViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PizzaDetailContent(
        uiState = uiState,
        onAction = { action ->
            if (action is PizzaDetailAction.OnClickBackButton) {
                onBack()
            } else {
                viewModel.onAction(action)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PizzaDetailContent(
    uiState: PizzaDetailUiState,
    onAction: (PizzaDetailAction) -> Unit
) {
    val gridState = rememberLazyGridState()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    BackButton(onAction = onAction)
                },
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (uiState.isLoading) {
                PizzaDetailSkeleton()
            } else {
                Column {
                    ImageContainer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(3f),
                        image = uiState.pizzaImage
                    )

                    Column(
                        Modifier
                            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                            .fillMaxWidth()
                            .weight(7f)
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        Name(name = uiState.pizzaName)
                        Spacer(Modifier.height(4.dp))
                        Ingredients(ingredients = uiState.ingredientsText)
                        Spacer(Modifier.height(16.dp))
                        Toppings(
                            gridState = gridState,
                            toppings = uiState.toppings,
                            onAction = onAction
                        )
                    }
                }

                AddToCartButton(
                    totalPrice = uiState.totalPrice,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
                        .align(Alignment.BottomCenter),
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
private fun PizzaDetailSkeleton() {
    Column(modifier = Modifier.fillMaxSize()) {
        SkeletonBox(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(7f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SkeletonBox(modifier = Modifier.size(width = 200.dp, height = 24.dp))
            SkeletonBox(modifier = Modifier.size(width = 280.dp, height = 16.dp))
            Spacer(modifier = Modifier.height(16.dp))
            SkeletonBox(modifier = Modifier.size(width = 150.dp, height = 18.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(3) {
                    SkeletonBox(
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun BackButton(
    onAction: (PizzaDetailAction) -> Unit
) {
    Row {
        Spacer(Modifier.width(16.dp))
        PrimaryIconButton(
            containerColor = TextSecondary8,
            modifier = Modifier.size(32.dp),
            onClick = { onAction(PizzaDetailAction.OnClickBackButton) },
            icon = {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.arrow_left_icon),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentDescription = null
                )
            }
        )
    }
}

@Composable
private fun ImageContainer(
    modifier: Modifier,
    image: String
) {
    Box(
        modifier
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun Name(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.title1Semibold,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 20.dp)
    )
}

@Composable
private fun Ingredients(ingredients: String) {
    Text(
        text = ingredients,
        style = MaterialTheme.typography.body3Regular.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
private fun AddToCartButton(
    modifier: Modifier,
    totalPrice: String,
    onAction: (PizzaDetailAction) -> Unit
) {
    PrimaryButton(
        text = "Add to cart for $totalPrice",
        onClick = { onAction(PizzaDetailAction.OnClickAddToCart) },
        modifier = modifier
    )
}

@Composable
private fun Toppings(
    gridState: LazyGridState,
    toppings: List<ToppingUi>,
    onAction: (PizzaDetailAction) -> Unit
) {
    Text(
        text = "ADD EXTRA TOPPINGS",
        style = MaterialTheme.typography.label2Semibold.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Spacer(Modifier.height(8.dp))
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .bottomFadingEdge(
                state = gridState,
                height = 120.dp,
                color = MaterialTheme.colorScheme.surface
            ),
        state = gridState,
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = toppings,
            key = { topping -> topping.id }
        ) { topping ->
            TopicCard(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onAction(PizzaDetailAction.OnIncrementTopping(topping.id)) },
                onClickDecreaseCountButton = {
                    onAction(PizzaDetailAction.OnDecreaseTopping(topping.id))
                },
                onClickIncreaseCountButton = {
                    onAction(PizzaDetailAction.OnIncrementTopping(topping.id))
                },
                topping = topping
            )
        }
        item(span = { GridItemSpan(3) }) {
            Spacer(Modifier.height(60.dp))
        }
    }
}
