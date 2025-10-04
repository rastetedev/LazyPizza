package com.raulastete.lazypizza.presentation.home.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.presentation.home.model.CountableProductUi
import com.raulastete.lazypizza.ui.components.GenericProductCard
import com.raulastete.lazypizza.ui.components.LPGhostButton
import com.raulastete.lazypizza.ui.components.LPIconButton
import com.raulastete.lazypizza.ui.components.ProductQuantityControl
import com.raulastete.lazypizza.ui.theme.AppTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CountableProductCard(
    modifier: Modifier = Modifier,
    countableProductUi: CountableProductUi,
    onClickAddToCart: () -> Unit,
    onClickIncreaseCount: () -> Unit,
    onClickDecreaseCount: () -> Unit,
    onClickRemoveFromCart: () -> Unit
) {

    GenericProductCard(
        image = "",
        modifier = modifier,
    ) {
        Column(
            Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (countableProductUi.isNotSelected) {
                ZeroItemsContent(
                    name = countableProductUi.name,
                    price = "$${countableProductUi.price}",
                    onClickAddToCart = onClickAddToCart,
                )
            } else {
                NonZeroItemsContent(
                    name = countableProductUi.name,
                    price = "$${countableProductUi.price}",
                    totalPrice = countableProductUi.totalPrice,
                    count = countableProductUi.count,
                    onClickIncreaseCount = onClickIncreaseCount,
                    onClickDecreaseCount = onClickDecreaseCount,
                    onClickRemoveFromCart = onClickRemoveFromCart,
                )
            }
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ColumnScope.ZeroItemsContent(
    name: String,
    price: String,
    onClickAddToCart: () -> Unit,
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = name,
        textAlign = TextAlign.Start,
        overflow = TextOverflow.Ellipsis,
        style = AppTheme.typography.body1Medium,
        color = AppTheme.colorScheme.textPrimary
    )

    Spacer(Modifier.weight(1f))

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = price,
            textAlign = TextAlign.Start,
            style = AppTheme.typography.title1Semibold,
            color = AppTheme.colorScheme.textPrimary
        )
        Spacer(Modifier.weight(1f))

        LPGhostButton(
            text = "Add to cart",
            onClick = onClickAddToCart
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ColumnScope.NonZeroItemsContent(
    name: String,
    price: String,
    totalPrice: String,
    count: Int,
    onClickIncreaseCount: () -> Unit,
    onClickDecreaseCount: () -> Unit,
    onClickRemoveFromCart: () -> Unit
) {

    Row(Modifier.fillMaxWidth()) {
        Text(
            text = name,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            style = AppTheme.typography.body1Medium,
            color = AppTheme.colorScheme.textPrimary
        )

        Spacer(Modifier.weight(1f))

        LPIconButton(
            icon = R.drawable.ic_trash,
            onClick = onClickRemoveFromCart
        )
    }

    Spacer(Modifier.weight(1f))

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProductQuantityControl(
            modifier = Modifier.weight(1f),
            count = count,
            onClickDecreaseCount = onClickDecreaseCount,
            onClickIncreaseCount = onClickIncreaseCount
        )

        Spacer(Modifier.weight(1f))

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = price,
                style = AppTheme.typography.title1Semibold,
                color = AppTheme.colorScheme.textPrimary
            )
            Text(
                "$count x $totalPrice",
                style = AppTheme.typography.body4Regular,
                color = AppTheme.colorScheme.textSecondary
            )
        }
    }
}

@Preview
@Composable
private fun PizzaCardPreview() {
    AppTheme {
        Column {
            CountableProductCard(
                modifier = Modifier.fillMaxWidth(),
                countableProductUi = CountableProductUi(
                    name = "Margharita",
                    price = 1.00,
                    count = 2,
                    imageUrl = ""
                ),
                onClickAddToCart = {},
                onClickDecreaseCount = {},
                onClickIncreaseCount = {},
                onClickRemoveFromCart = {}
            )

            CountableProductCard(
                modifier = Modifier.fillMaxWidth(),
                countableProductUi = CountableProductUi(
                    name = "Margharita",
                    price = 1.00,
                    count = 0,
                    imageUrl = ""
                ),
                onClickAddToCart = {},
                onClickDecreaseCount = {},
                onClickIncreaseCount = {},
                onClickRemoveFromCart = {}
            )
        }
    }
}