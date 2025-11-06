package com.raulastete.lazypizza.presentation.pizza_detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@Composable
fun PizzaInfo(modifier: Modifier, pizzaName: String, pizzaDescription: String) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            pizzaName,
            style = AppTheme.typography.title1Semibold,
            color = AppTheme.colorScheme.textPrimary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Text(
            pizzaDescription,
            style = AppTheme.typography.body3Regular,
            color = AppTheme.colorScheme.textSecondary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
    }
}