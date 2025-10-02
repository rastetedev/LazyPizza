package com.raulastete.lazypizza.presentation.product_detail.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.ui.components.ProductQuantityControl
import com.raulastete.lazypizza.ui.theme.AppTheme

@Composable
fun ToppingCard(
    modifier: Modifier = Modifier,
    productName: String,
    price: String,
    count: Int,
    onClick: () -> Unit,
    onClickDecreaseCount: () -> Unit,
    onClickIncreaseCount: () -> Unit
) {

    Card(
        modifier = modifier,
        onClick = onClick,
        border = BorderStroke(
            width = 1.dp,
            color = if (count == 0) AppTheme.colorScheme.textSecondary else AppTheme.colorScheme.primary
        ),
        shape = AppTheme.shape.card,
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colorScheme.surfaceHigher
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProductImage("")
            Spacer(Modifier.height(12.dp))
            Text(
                text = productName,
                style = AppTheme.typography.body3Regular,
                color = AppTheme.colorScheme.textSecondary
            )
            Spacer(Modifier.height(12.dp))
            if (count == 0)
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = price,
                    style = AppTheme.typography.title2,
                    color = AppTheme.colorScheme.textPrimary
                )
            else {
                ProductQuantityControl(
                    modifier = Modifier.fillMaxWidth(),
                    count = count,
                    onClickDecreaseCount = onClickDecreaseCount,
                    onClickIncreaseCount = onClickIncreaseCount
                )
            }

        }
    }
}

@Composable
private fun ProductImage(image: String) {

    Box(
        Modifier
            .size(64.dp)
            .background(color = AppTheme.colorScheme.primary8, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        //TODO: Load Image
    }

}