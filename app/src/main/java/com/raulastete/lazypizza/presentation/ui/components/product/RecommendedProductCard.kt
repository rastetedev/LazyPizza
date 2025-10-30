package com.raulastete.lazypizza.presentation.ui.components.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.presentation.ui.components.LPIconButton
import com.raulastete.lazypizza.presentation.ui.model.RecommendedProductCardUi
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@Composable
fun RecommendedProductCard(
    modifier: Modifier = Modifier,
    recommendedProductCardUi: RecommendedProductCardUi,
    onAddToCartClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = AppTheme.shape.card,
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colorScheme.surfaceHigher,
            disabledContainerColor = AppTheme.colorScheme.surfaceHigher
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        )
    ) {

        val imageContainerShape = RoundedCornerShape(
            topStart = 12.dp,
            bottomStart = 0.dp,
            topEnd = 12.dp,
            bottomEnd = 0.dp
        )

        Column(
            modifier = Modifier
                .padding(2.dp)
                .width(IntrinsicSize.Min)
        ) {
            Box(
                Modifier
                    .width(160.dp)
                    .height(120.dp)
                    .clip(shape = imageContainerShape)
                    .background(color = AppTheme.colorScheme.surfaceHighest),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(model = recommendedProductCardUi.imageUrl, contentDescription = null)
            }

            Spacer(Modifier.height(12.dp))

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 12.dp)
            ) {
                Text(
                    text = recommendedProductCardUi.name,
                    style = AppTheme.typography.body1Regular.copy(color = AppTheme.colorScheme.textSecondary)
                )

                Spacer(Modifier.height(8.dp))

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = recommendedProductCardUi.unitPrice,
                        style = AppTheme.typography.title1Semibold.copy(color = AppTheme.colorScheme.textPrimary)
                    )

                    LPIconButton(
                        icon = R.drawable.ic_plus,
                        onClick = onAddToCartClick
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun RecommendedProductCardPreview() {
    AppTheme {
        RecommendedProductCard(
            recommendedProductCardUi = RecommendedProductCardUi(
                id = "1",
                name = "Pizza",
                imageUrl = "",
                unitPrice = "$12.99"
            ),
            onAddToCartClick = {}
        )
    }
}