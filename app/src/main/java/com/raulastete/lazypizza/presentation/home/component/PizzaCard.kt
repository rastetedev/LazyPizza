package com.raulastete.lazypizza.presentation.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.presentation.model.PizzaUi
import com.raulastete.lazypizza.ui.component.ProductCardWrapper
import com.raulastete.lazypizza.ui.theme.body1Medium
import com.raulastete.lazypizza.ui.theme.body3Regular
import com.raulastete.lazypizza.ui.theme.title1Semibold

@Composable
fun PizzaCard(
    pizza: PizzaUi,
    onClick: () -> Unit,
    modifier: Modifier
) {
    ProductCardWrapper(
        modifier = modifier,
        image = pizza.image,
        onClick = onClick
    ) {
        Details(
            pizza.name,
            pizza.ingredients,
            pizza.unitPrice
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
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = ingredients,
            style = MaterialTheme.typography.body3Regular.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = price,
            style = MaterialTheme.typography.title1Semibold,
            maxLines = 1
        )
    }
}