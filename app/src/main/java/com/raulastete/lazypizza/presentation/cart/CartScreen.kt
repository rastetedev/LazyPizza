package com.raulastete.lazypizza.presentation.cart

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.presentation.ui.components.LPCenterTopbar
import com.raulastete.lazypizza.presentation.ui.components.MessageFullScreen
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@Composable
fun CartScreen(navigateToMenu: () -> Unit) {
    CartScreenContent(
        navigateToMenu = navigateToMenu
    )
}

@Composable
private fun CartScreenContent(
    navigateToMenu: () -> Unit
) {
    Scaffold(
        topBar = {
            LPCenterTopbar(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.cart_screen)
            )
        },
        containerColor = AppTheme.colorScheme.background
    ) { paddingValues ->

        MessageFullScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            title = stringResource(R.string.cart_empty_title),
            subtitle = stringResource(R.string.cart_empty_subtitle),
            buttonText = stringResource(R.string.cart_empty_button),
            onClick = navigateToMenu
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CartScreenContentPreview() {
    AppTheme {
        CartScreenContent(
            navigateToMenu = {}
        )
    }
}