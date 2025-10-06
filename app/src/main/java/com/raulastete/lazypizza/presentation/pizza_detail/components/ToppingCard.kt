package com.raulastete.lazypizza.presentation.pizza_detail.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.raulastete.lazypizza.presentation.pizza_detail.model.ToppingUi
import com.raulastete.lazypizza.ui.components.ProductQuantityControl
import com.raulastete.lazypizza.ui.theme.AppTheme

@Composable
fun ToppingCard(
    modifier: Modifier = Modifier,
    toppingUi: ToppingUi,
    onClick: () -> Unit,
    onClickDecreaseCount: () -> Unit,
    onClickIncreaseCount: () -> Unit
) {
    Box(
        modifier = modifier
            .clickable(
                onClick = onClick,
                enabled = toppingUi.isNotSelected,
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
                color = if (toppingUi.isNotSelected) AppTheme.colorScheme.outline else AppTheme.colorScheme.primary,
                shape = AppTheme.shape.card
            )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ToppingImage(imageUrl = toppingUi.imageUrl)
            Spacer(Modifier.height(12.dp))
            ToppingName(name = toppingUi.name)
            Spacer(Modifier.height(12.dp))
            if (toppingUi.isNotSelected)
                ToppingPrice(price = toppingUi.price)
            else {
                ProductQuantityControl(
                    modifier = Modifier.fillMaxWidth(),
                    count = toppingUi.count,
                    onClickDecreaseCount = onClickDecreaseCount,
                    onClickIncreaseCount = onClickIncreaseCount
                )
            }
        }
    }
}

@Composable
private fun ToppingName(name: String) {
    Text(
        text = name,
        style = AppTheme.typography.body3Regular,
        color = AppTheme.colorScheme.textSecondary
    )
}

@Composable
private fun ToppingPrice(price: Double) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = "$${price}",
        style = AppTheme.typography.title2,
        color = AppTheme.colorScheme.textPrimary
    )
}

@Composable
private fun ToppingImage(imageUrl: String) {
    Box(
        Modifier
            .size(64.dp)
            .background(color = AppTheme.colorScheme.primary8, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
        )
    }
}