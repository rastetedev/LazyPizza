package com.raulastete.lazypizza.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LPSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: String = "Search for delicious food...",
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .height(56.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(28.dp)),
        placeholder = {
            Text(
                text = placeholder,
                style = AppTheme.typography.body1Regular,
                color = AppTheme.colorScheme.textSecondary
            )
        },
        textStyle = AppTheme.typography.body1Regular,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = AppTheme.colorScheme.surfaceHigher,
            focusedContainerColor = AppTheme.colorScheme.surfaceHigher,
            unfocusedBorderColor = AppTheme.colorScheme.surfaceHigher,
            focusedBorderColor = AppTheme.colorScheme.surfaceHigher,
            cursorColor = AppTheme.colorScheme.primary
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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                query = "",
                onQueryChange = {}
            )
            LPSearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                query = "Margherita",
                onQueryChange = {}
            )
        }
    }
}