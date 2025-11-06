package com.raulastete.lazypizza.presentation.menu.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.domain.entity.Product
import com.raulastete.lazypizza.presentation.ui.components.designsystem.LPGhostButton
import com.raulastete.lazypizza.presentation.ui.components.designsystem.LPIconButton
import com.raulastete.lazypizza.presentation.ui.components.product.ProductQuantityControl
import com.raulastete.lazypizza.presentation.ui.components.product.ProductWrapperCard
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun GenericProductCard(
    modifier: Modifier = Modifier,
    product: Product,
    count: Int,
    totalPrice: String,
    onClickAddToCart: () -> Unit,
    onClickIncreaseCount: () -> Unit,
    onClickDecreaseCount: () -> Unit,
    onClickRemoveFromCart: () -> Unit
) {

    ProductWrapperCard(
        imageUrl = product.imageUrl,
        modifier = modifier,
    ) {
        Column(
            Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (count == 0) {
                ZeroItemsContent(
                    name = product.name,
                    price = "$${product.unitPrice}",
                    onClickAddToCart = onClickAddToCart,
                )
            } else {
                NonZeroItemsContent(
                    name = product.name,
                    price = "$${product.unitPrice}",
                    totalPrice = totalPrice,
                    count = count,
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
private fun ZeroItemsContent(
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

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = price,
            autoSize = TextAutoSize.StepBased(
                minFontSize = 12.sp,
                maxFontSize = AppTheme.typography.title1Semibold.fontSize,
            ),
            maxLines = 1,
            textAlign = TextAlign.Start,
            style = AppTheme.typography.title1Semibold,
            color = AppTheme.colorScheme.textPrimary
        )
        Row {
            Spacer(Modifier.width(8.dp))
            LPGhostButton(
                modifier = Modifier.requiredSizeIn(minWidth = 85.dp),
                text = "Add to cart",
                onClick = onClickAddToCart
            )
        }
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

    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            modifier = Modifier.weight(1f),
            text = name,
            textAlign = TextAlign.Start,
            maxLines = 2,
            overflow = TextOverflow.Clip,
            style = AppTheme.typography.body1Medium,
            color = AppTheme.colorScheme.textPrimary
        )
        Spacer(Modifier.width(8.dp))
        LPIconButton(
            icon = R.drawable.ic_trash,
            onClick = onClickRemoveFromCart
        )
    }

    Spacer(Modifier.weight(1f))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProductQuantityControl(
            count = count,
            onClickDecreaseCount = onClickDecreaseCount,
            onClickIncreaseCount = onClickIncreaseCount
        )

        Column(
            modifier = Modifier.padding(start = 8.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = price,
                autoSize = TextAutoSize.StepBased(
                    minFontSize = 12.sp,
                    maxFontSize = AppTheme.typography.title1Semibold.fontSize,
                ),
                maxLines = 1,
                style = AppTheme.typography.title1Semibold,
                color = AppTheme.colorScheme.textPrimary
            )
            Text(
                "$count x $totalPrice",
                autoSize = TextAutoSize.StepBased(
                    minFontSize = 12.sp,
                    maxFontSize = AppTheme.typography.body4Regular.fontSize,
                ),
                maxLines = 1,
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
                product = Product(
                    id = "",
                    imageUrl = "",
                    name = "Margharita",
                    description = "",
                    categoryId = "",
                    unitPrice = 1.00,
                ),
                totalPrice = "$2.00",
                count = 2,
                onClickAddToCart = {},
                onClickDecreaseCount = {},
                onClickIncreaseCount = {},
                onClickRemoveFromCart = {}
            )

            GenericProductCard(
                modifier = Modifier.fillMaxWidth(),
                product = Product(
                    id = "",
                    imageUrl = "",
                    name = "Margharita",
                    description = "",
                    categoryId = "",
                    unitPrice = 1.00,
                ),
                totalPrice = "$0.00",
                count = 0,
                onClickAddToCart = {},
                onClickDecreaseCount = {},
                onClickIncreaseCount = {},
                onClickRemoveFromCart = {}
            )
        }
    }
}