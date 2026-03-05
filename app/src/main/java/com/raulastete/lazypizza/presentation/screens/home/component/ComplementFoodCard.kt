package com.raulastete.lazypizza.presentation.screens.home.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.presentation.model.ComplementFoodUi
import com.raulastete.lazypizza.presentation.component.Counter
import com.raulastete.lazypizza.presentation.component.ProductCardWrapper
import com.raulastete.lazypizza.presentation.component.SecondaryIconButton
import com.raulastete.lazypizza.presentation.component.SecondaryTextButton
import com.raulastete.lazypizza.presentation.theme.LazyPizzaTheme
import com.raulastete.lazypizza.presentation.theme.body1Medium
import com.raulastete.lazypizza.presentation.theme.body4Regular
import com.raulastete.lazypizza.presentation.theme.title1Semibold

@Composable
fun ComplementFoodCard(
    complementFood: ComplementFoodUi,
    modifier: Modifier,
    onClickAddToCartButton: () -> Unit,
    onClickDeleteFromCartButton: () -> Unit,
    onClickDecreaseCountButton: () -> Unit,
    onClickIncreaseCountButton: () -> Unit
) {
    ProductCardWrapper(
        modifier = modifier,
        image = complementFood.image
    ) {
        Details(
            details = complementFood,
            onClickAddToCartButton = onClickAddToCartButton,
            onClickDeleteFromCartButton = onClickDeleteFromCartButton,
            onClickDecreaseCountButton = onClickDecreaseCountButton,
            onClickIncreaseCountButton = onClickIncreaseCountButton
        )
    }
}

@Composable
private fun Details(
    details: ComplementFoodUi,
    onClickAddToCartButton: () -> Unit,
    onClickDeleteFromCartButton: () -> Unit,
    onClickDecreaseCountButton: () -> Unit,
    onClickIncreaseCountButton: () -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        Header(
            name = details.name,
            count = details.count,
            onClickDeleteFromCartButton = onClickDeleteFromCartButton
        )
        Spacer(Modifier.weight(1f))
        Footer(
            count = details.count,
            unitPrice = details.unitPrice,
            totalPrice = details.totalPrice,
            onClickAddToCartButton = onClickAddToCartButton,
            onClickDecreaseCountButton = onClickDecreaseCountButton,
            onClickIncreaseCountButton = onClickIncreaseCountButton
        )
    }
}

@Composable
private fun Header(
    name: String,
    count: Int,
    onClickDeleteFromCartButton: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = name,
            style = MaterialTheme.typography.body1Medium,
            maxLines = 1
        )
        Spacer(Modifier.weight(1f))
        DeleteFromCartButton(
            visible = count > 0,
            onClick = onClickDeleteFromCartButton
        )
    }
}

@Composable
private fun DeleteFromCartButton(
    visible: Boolean,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(
            initialOffsetX = { it }
        ) + fadeIn(),
        exit = slideOutHorizontally(
            targetOffsetX = { it }
        ) + fadeOut()
    ) {
        SecondaryIconButton(
            modifier = Modifier.size(22.dp),
            onClick = onClick
        ){
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.trash_icon),
                contentDescription = null,
                modifier = Modifier.size(22.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
private fun Footer(
    count: Int,
    unitPrice: String,
    totalPrice: String,
    onClickAddToCartButton: () -> Unit,
    onClickDecreaseCountButton: () -> Unit,
    onClickIncreaseCountButton: () -> Unit
) {
    AnimatedContent(
        modifier = Modifier.fillMaxWidth(),
        targetState = count > 0,
        label = "PriceTransition"
    ) { hasCount ->
        if (hasCount) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Counter(
                    count = count,
                    onClickDecreaseCountButton = onClickDecreaseCountButton,
                    onClickIncreaseCountButton = onClickIncreaseCountButton
                )
                PricesInfo(
                    count = count,
                    unitPrice = unitPrice,
                    totalPrice = totalPrice
                )
            }
        } else {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = unitPrice,
                    style = MaterialTheme.typography.title1Semibold,
                    maxLines = 1
                )
                AddToCartButton(onClick = onClickAddToCartButton)
            }
        }
    }
}

@Composable
private fun AddToCartButton(onClick: () -> Unit) {
    SecondaryTextButton(
        text = stringResource(R.string.add_to_cart),
        onClick = onClick
    )
}

@Composable
private fun PricesInfo(
    count: Int,
    unitPrice: String,
    totalPrice: String
) {
    Column(horizontalAlignment = Alignment.End) {
        Text(
            text = totalPrice,
            style = MaterialTheme.typography.title1Semibold
        )
        Text(
            text = "$count x $unitPrice",
            style = MaterialTheme.typography.body4Regular.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}

@Composable
@Preview(name = "With count")
private fun CartCardPreview1() {
    LazyPizzaTheme {
        ComplementFoodCard(
            modifier = Modifier.fillMaxWidth(),
            complementFood = ComplementFoodUi(
                id = "",
                image = "",
                name = "Margherita",
                unitPrice = "$8.99",
                count = 2,
                totalPrice = "$17.98"
            ),
            onClickAddToCartButton = { },
            onClickDeleteFromCartButton = { },
            onClickDecreaseCountButton = { },
            onClickIncreaseCountButton = { }
        )
    }
}

@Composable
@Preview(name = "Without count")
private fun CartCardPreview2() {
    LazyPizzaTheme {
        ComplementFoodCard(
            modifier = Modifier.fillMaxWidth(),
            complementFood = ComplementFoodUi(
                id = "",
                image = "",
                name = "Margherita",
                unitPrice = "$8.99",
                count = 0,
                totalPrice = "$17.98"
            ),
            onClickAddToCartButton = { },
            onClickDeleteFromCartButton = { },
            onClickDecreaseCountButton = { },
            onClickIncreaseCountButton = { }
        )
    }
}