package com.raulastete.lazypizza.presentation.ui.components.product

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
import com.raulastete.lazypizza.presentation.ui.components.LPGhostButton
import com.raulastete.lazypizza.presentation.ui.components.LPIconButton
import com.raulastete.lazypizza.presentation.ui.components.ProductQuantityControl
import com.raulastete.lazypizza.presentation.ui.model.GenericProductCardUi
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun GenericProductCard(
    modifier: Modifier = Modifier,
    genericProductCardUi: GenericProductCardUi,
    onClickAddToCart: () -> Unit,
    onClickIncreaseCount: () -> Unit,
    onClickDecreaseCount: () -> Unit,
    onClickRemoveFromCart: () -> Unit
) {

    ProductWrapperCard(
        imageUrl = genericProductCardUi.imageUrl,
        modifier = modifier,
    ) {
        Column(
            Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (genericProductCardUi.isNotSelected) {
                ZeroItemsContent(
                    name = genericProductCardUi.name,
                    price = genericProductCardUi.unitPrice,
                    onClickAddToCart = onClickAddToCart,
                )
            } else {
                NonZeroItemsContent(
                    name = genericProductCardUi.name,
                    price = genericProductCardUi.unitPrice,
                    totalPrice = genericProductCardUi.totalPrice,
                    count = genericProductCardUi.count,
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
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            GenericProductCard(
                modifier = Modifier.fillMaxWidth(),
                genericProductCardUi = GenericProductCardUi(
                    id = "",
                    imageUrl = "",
                    name = "Margharita",
                    unitPrice = "$1.00",
                    totalPrice = "$2.00",
                    count = 2
                ),
                onClickAddToCart = {},
                onClickDecreaseCount = {},
                onClickIncreaseCount = {},
                onClickRemoveFromCart = {}
            )

            GenericProductCard(
                modifier = Modifier.fillMaxWidth(),
                genericProductCardUi = GenericProductCardUi(
                    id = "",
                    imageUrl = "",
                    name = "Margharita",
                    unitPrice = "$1.00",
                    totalPrice = "$2.00",
                    count = 0
                ),
                onClickAddToCart = {},
                onClickDecreaseCount = {},
                onClickIncreaseCount = {},
                onClickRemoveFromCart = {}
            )
        }
    }
}