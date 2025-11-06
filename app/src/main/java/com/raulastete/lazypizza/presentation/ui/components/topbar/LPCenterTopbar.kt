package com.raulastete.lazypizza.presentation.ui.components.topbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LPCenterTopbar(
    modifier: Modifier,
    title: String,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(title, style = AppTheme.typography.body1Medium)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppTheme.colorScheme.background
        )
    )
}

@Preview
@Composable
private fun LPCenterTopbarPreview() {
    AppTheme {
        LPCenterTopbar(modifier = Modifier.fillMaxWidth(), title = "Order History")
    }
}