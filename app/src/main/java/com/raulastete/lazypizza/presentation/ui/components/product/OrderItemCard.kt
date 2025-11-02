package com.raulastete.lazypizza.presentation.ui.components.product

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.raulastete.lazypizza.presentation.ui.components.LPIconButton
import com.raulastete.lazypizza.presentation.ui.components.ProductQuantityControl
import com.raulastete.lazypizza.presentation.ui.model.OrderItemCardUi
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun OrderItemCard(
    modifier: Modifier = Modifier,
    orderItemCardUi: OrderItemCardUi,
    onClickIncreaseCount: () -> Unit,
    onClickDecreaseCount: () -> Unit,
    onClickRemoveFromCart: () -> Unit
) {

    ProductWrapperCard(
        imageUrl = orderItemCardUi.imageUrl,
        modifier = modifier,
    ) {
        Column(
            Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = orderItemCardUi.name,
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

            orderItemCardUi.toppings?.let { toppings ->
                toppings.forEach { topping ->
                    Text(
                        text = topping,
                        style = AppTheme.typography.body3Regular,
                        color = AppTheme.colorScheme.textSecondary
                    )
                }
                Spacer(Modifier.height(8.dp))
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProductQuantityControl(
                    count = orderItemCardUi.count,
                    onClickDecreaseCount = onClickDecreaseCount,
                    onClickIncreaseCount = onClickIncreaseCount,
                    isDecreaseButtonEnabled = orderItemCardUi.canDecreaseQuantity
                )

                Spacer(Modifier.weight(1f))

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = orderItemCardUi.totalPrice,
                        style = AppTheme.typography.title1Semibold,
                        color = AppTheme.colorScheme.textPrimary
                    )
                    Text(
                        "${orderItemCardUi.count} x ${orderItemCardUi.unitPrice}",
                        style = AppTheme.typography.body4Regular,
                        color = AppTheme.colorScheme.textSecondary
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun OrderItemCardPreview() {
    AppTheme {
        OrderItemCard(
            orderItemCardUi = OrderItemCardUi(
                id = 0L,
                imageUrl = "https://cdn.pixabay.com/photo/2016/0",
                name = "Pizza",
                unitPrice = "$10.00",
                toppings = listOf("Topping 1", "Topping 2"),
                totalPrice = "$20.00",
                count = 2,
            ),
            onClickDecreaseCount = {},
            onClickIncreaseCount = {},
            onClickRemoveFromCart = {}
        )
    }
}