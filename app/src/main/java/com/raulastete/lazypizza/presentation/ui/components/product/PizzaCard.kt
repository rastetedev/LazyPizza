package com.raulastete.lazypizza.presentation.ui.components.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.presentation.ui.model.PizzaCardUi
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@Composable
fun PizzaCard(
    modifier: Modifier = Modifier,
    pizzaCardUi: PizzaCardUi,
    onClick: () -> Unit
) {
    ProductWrapperCard(
        imageUrl = pizzaCardUi.imageUrl,
        modifier = modifier,
        onClick = onClick
    ) {
        Column(
            Modifier
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = pizzaCardUi.name,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    minLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = AppTheme.typography.body1Medium,
                    color = AppTheme.colorScheme.textPrimary
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = pizzaCardUi.description,
                    style = AppTheme.typography.body3Regular,
                    color = AppTheme.colorScheme.textSecondary,
                    textAlign = TextAlign.Start,
                    maxLines = 2,
                    minLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(Modifier.weight(1f))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = pizzaCardUi.unitPrice,
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
        PizzaCard(
            Modifier.fillMaxWidth(),
            pizzaCardUi = PizzaCardUi(
                id = "",
                name = "Margherita",
                unitPrice = "$10.0",
                description = "This is my pizza description",
                imageUrl = ""
            ),
            onClick = {}
        )
    }
}