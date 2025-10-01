package com.raulastete.lazypizza.presentation.home.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.ui.components.LPChip
import com.raulastete.lazypizza.ui.theme.AppTheme

@Composable
fun CategoryListRow(
    modifier: Modifier = Modifier,
    categories: List<String>,
    onCategorySelected: (String) -> Unit,
) {

    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(categories) { index, category ->
            LPChip(
                text = category,
                onClick = { onCategorySelected(category) }
            )

            if (index < categories.size - 1) {
                Spacer(modifier = Modifier.width(8.dp))
            }
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