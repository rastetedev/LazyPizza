package com.raulastete.lazypizza.presentation.pizza_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.raulastete.lazypizza.domain.entity.Topping
import com.raulastete.lazypizza.presentation.ui.components.ProductQuantityControl
import com.raulastete.lazypizza.presentation.ui.model.ToppingCardUi
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@Composable
fun ToppingCard(
    modifier: Modifier = Modifier,
    toppingCardUi: ToppingCardUi,
    onClick: () -> Unit,
    onClickDecreaseCount: () -> Unit,
    onClickIncreaseCount: () -> Unit
) {
    Box(
        modifier = modifier
            .clickable(
                onClick = onClick,
                enabled = toppingCardUi.isSelected.not(),
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
            .shadow(elevation = 2.dp, shape = AppTheme.shape.card)
            .background(
                color = AppTheme.colorScheme.surfaceHigher,
                shape = AppTheme.shape.card
            )
            .border(
                width = 1.dp,
                color = if (toppingCardUi.isSelected.not()) AppTheme.colorScheme.outline else AppTheme.colorScheme.primary,
                shape = AppTheme.shape.card
            )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .size(64.dp)
                    .background(color = AppTheme.colorScheme.primary8, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = toppingCardUi.topping.imageUrl,
                    contentDescription = null,
                )
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = toppingCardUi.topping.name,
                style = AppTheme.typography.body3Regular,
                color = AppTheme.colorScheme.textSecondary,
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(
                    minFontSize = 10.sp,
                    maxFontSize = AppTheme.typography.body3Regular.fontSize
                )
            )
            Spacer(Modifier.height(12.dp))
            if (toppingCardUi.isSelected.not())
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "$${toppingCardUi.topping.unitPrice}",
                    style = AppTheme.typography.title2,
                    color = AppTheme.colorScheme.textPrimary
                )
            else {
                ProductQuantityControl(
                    modifier = Modifier.fillMaxWidth(),
                    count = toppingCardUi.count,
                    isIncreaseButtonEnabled = toppingCardUi.canIncreaseQuantity,
                    onClickDecreaseCount = onClickDecreaseCount,
                    onClickIncreaseCount = onClickIncreaseCount
                )
            }
        }
    }
}

@Composable
@Preview
private fun ToppingCardPreview() {
    AppTheme {
        ToppingCard(
            modifier = Modifier.width(120.dp),
            toppingCardUi = ToppingCardUi(
                topping = Topping(
                    id = "",
                    imageUrl = "",
                    name = "Tomate",
                    unitPrice = 1.00,
                ),
                count = 1,
            ),
            onClickDecreaseCount = {},
            onClickIncreaseCount = {},
            onClick = {}
        )
    }
}