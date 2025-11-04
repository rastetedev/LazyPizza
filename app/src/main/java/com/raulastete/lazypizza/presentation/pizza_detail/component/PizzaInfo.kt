package com.raulastete.lazypizza.presentation.pizza_detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.domain.entity.Product
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@Composable
fun PizzaInfo(modifier: Modifier, product: Product) {
    Column(
        modifier = modifier
    ) {
        Text(
            product.name,
            style = AppTheme.typography.title1Semibold,
            color = AppTheme.colorScheme.textPrimary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(Modifier.height(4.dp))
        Text(
            product.description,
            style = AppTheme.typography.body3Regular,
            color = AppTheme.colorScheme.textSecondary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
    }
}