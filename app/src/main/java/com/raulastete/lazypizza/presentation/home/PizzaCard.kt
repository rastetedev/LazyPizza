package com.raulastete.lazypizza.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.ui.component.ItemCardWrapper
import com.raulastete.lazypizza.ui.theme.body1Medium
import com.raulastete.lazypizza.ui.theme.body3Regular
import com.raulastete.lazypizza.ui.theme.title1Semibold

data class PizzaCardDetails(
    val image: String,
    val name: String,
    val ingredients: String,
    val unitPrice: String
)

@Composable
fun PizzaCard(
    details: PizzaCardDetails,
    modifier: Modifier
) {
    ItemCardWrapper(
        modifier = modifier,
        image = details.image
    ) {
        Details(
            details.name,
            details.ingredients,
            details.unitPrice
        )
    }
}

@Composable
private fun Details(
    name: String,
    ingredients: String,
    price: String
) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            text = name,
            style = MaterialTheme.typography.body1Medium,
            maxLines = 1
        )
        Text(
            text = ingredients,
            style = MaterialTheme.typography.body3Regular.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
            maxLines = 2
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = price,
            style = MaterialTheme.typography.title1Semibold,
            maxLines = 1
        )
    }
}