package com.raulastete.lazypizza.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.ui.theme.AppTheme

@Composable
fun LPSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: String = "Search for delicious food...",
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.height(56.dp),
        placeholder = {
            Text(
                text = placeholder,
                style = AppTheme.typography.body1Regular,
                color = AppTheme.colorScheme.textSecondary
            )
        },
        textStyle = AppTheme.typography.body1Regular,
        singleLine = true,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = AppTheme.colorScheme.surfaceHigher,
            focusedContainerColor = AppTheme.colorScheme.surfaceHigher,
        ),
        shape = RoundedCornerShape(28.dp),
        leadingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                contentDescription = "Search Icon",
                tint = AppTheme.colorScheme.primary
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text
        )
    )
}

@Preview
@Composable
private fun LPSearchBarPreview() {
    AppTheme {
        Column {
            LPSearchBar(
                modifier = Modifier.fillMaxWidth().height(56.dp),
                query = "",
                onQueryChange = {}
            )
            LPSearchBar(
                modifier = Modifier.fillMaxWidth().height(56.dp),
                query = "Margherita",
                onQueryChange = {}
            )
        }
    }
}