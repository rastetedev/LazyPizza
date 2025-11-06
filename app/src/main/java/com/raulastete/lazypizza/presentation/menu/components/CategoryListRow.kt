package com.raulastete.lazypizza.presentation.menu.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.presentation.ui.components.designsystem.LPChip
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@Composable
fun CategoryListRow(
    modifier: Modifier = Modifier,
    categories: List<String>,
    onCategorySelected: (String) -> Unit,
) {
    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(categories) { index, category ->
            LPChip(
                text = category,
                onClick = { onCategorySelected(category) }
            )
        }
    }
}

@Preview
@Composable
private fun CategoryListRowPreview() {
    AppTheme {
        CategoryListRow(
            categories = listOf(
                "Pizza", "Burgers", "Sushi", "Desserts", "Drinks"
            )
        ) {}
    }
}