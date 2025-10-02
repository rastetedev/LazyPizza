package com.raulastete.lazypizza.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.ui.components.GenericProductCard
import com.raulastete.lazypizza.ui.theme.AppTheme

@Composable
fun PizzaCard(
    modifier: Modifier = Modifier,
    image: String,
    onClick: (Int) -> Unit
) {

    GenericProductCard(
        image = image,
        modifier = modifier,
        onClick = { onClick(0) }
    ) {
        Column(
            Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Margherita",
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    style = AppTheme.typography.body1Medium,
                    color = AppTheme.colorScheme.textPrimary
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Tomato sauce, mozarella, fresh olive olive olive",
                    style = AppTheme.typography.body3Regular,
                    color = AppTheme.colorScheme.textSecondary,
                    textAlign = TextAlign.Start,
                    minLines = 2
                )
            }

            Spacer(Modifier.weight(1f))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "$8.99",
                textAlign = TextAlign.Start,
                style = AppTheme.typography.title1Semibold,
                color = AppTheme.colorScheme.textPrimary
            )
        }
    }
}

@Preview
@Composable
private fun PizzaCardPreview() {
    AppTheme {
        PizzaCard(Modifier.fillMaxWidth(), image = "") {

        }
    }
}