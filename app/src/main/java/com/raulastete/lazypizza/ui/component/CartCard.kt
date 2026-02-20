package com.raulastete.lazypizza.ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.ui.theme.LazyPizzaTheme
import com.raulastete.lazypizza.ui.theme.body1Medium
import com.raulastete.lazypizza.ui.theme.body4Regular
import com.raulastete.lazypizza.ui.theme.title1Semibold

data class CountableProductDetails(
    val image: String,
    val name: String,
    val unitPrice: String,
    val totalPrice: String,
    val count: Int = 0
)

@Composable
fun CountableProductCard(
    details: CountableProductDetails,
    modifier: Modifier,
    onClickAddToCartButton: () -> Unit,
    onClickDeleteFromCartButton: () -> Unit,
    onClickDecreaseCountButton: () -> Unit = {},
    onClickIncreaseCountButton: () -> Unit = {}
) {
    ItemCardWrapper(
        modifier = modifier,
        image = details.image
    ) {
        Details(
            details.name,
            details.unitPrice,
            details.totalPrice,
            details.count,
            onClickAddToCartButton = onClickAddToCartButton,
            onClickDeleteFromCartButton = onClickDeleteFromCartButton,
            onClickDecreaseCountButton = onClickDecreaseCountButton,
            onClickIncreaseCountButton = onClickIncreaseCountButton
        )
    }
}

@Composable
private fun Details(
    name: String,
    unitPrice: String,
    totalPrice: String,
    count: Int,
    onClickAddToCartButton: () -> Unit,
    onClickDeleteFromCartButton: () -> Unit,
    onClickDecreaseCountButton: () -> Unit,
    onClickIncreaseCountButton: () -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        Header(
            name = name,
            count = count,
            onClickDeleteFromCartButton = onClickDeleteFromCartButton
        )
        Spacer(Modifier.weight(1f))
        Footer(
            count = count,
            unitPrice = unitPrice,
            totalPrice = totalPrice,
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
            icon = ImageVector.vectorResource(R.drawable.trash_icon),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(22.dp),
            onClick = onClick
        )
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
private fun AddToCartButton(
    onClick: () -> Unit
) {
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
private fun Counter(
    count: Int,
    onClickDecreaseCountButton: () -> Unit,
    onClickIncreaseCountButton: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        SecondaryIconButton(
            icon = ImageVector.vectorResource(R.drawable.minus_icon),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            onClick = onClickDecreaseCountButton,
            modifier = Modifier.size(22.dp)
        )
        Box(Modifier.width(52.dp), contentAlignment = Alignment.Center) {
            Text("$count", textAlign = TextAlign.Center)
        }
        SecondaryIconButton(
            icon = ImageVector.vectorResource(R.drawable.plus_icon),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            onClick = onClickIncreaseCountButton,
            modifier = Modifier.size(22.dp)
        )
    }
}

@Composable
@Preview(name = "With count")
private fun CountableProductCardPreview1() {
    LazyPizzaTheme {
        CountableProductCard(
            details = CountableProductDetails(
                image = "",
                name = "Margherita",
                unitPrice = "$8.99",
                count = 2,
                totalPrice = "$17.98"
            ),
            modifier = Modifier.fillMaxWidth(),
            onClickAddToCartButton = { },
            onClickDeleteFromCartButton = { },
            onClickDecreaseCountButton = { },
            onClickIncreaseCountButton = { }
        )
    }
}

@Composable
@Preview(name = "Without count")
private fun CountableProductCardPreview2() {
    LazyPizzaTheme {
        CountableProductCard(
            details = CountableProductDetails(
                image = "",
                name = "Margherita",
                unitPrice = "$8.99",
                count = 0,
                totalPrice = "$17.98"
            ),
            modifier = Modifier.fillMaxWidth(),
            onClickAddToCartButton = { },
            onClickDeleteFromCartButton = { },
            onClickDecreaseCountButton = { },
            onClickIncreaseCountButton = { }
        )
    }
}