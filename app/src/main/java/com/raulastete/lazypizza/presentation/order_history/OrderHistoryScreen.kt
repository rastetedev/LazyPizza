package com.raulastete.lazypizza.presentation.order_history

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.presentation.ui.components.topbar.LPCenterTopbar
import com.raulastete.lazypizza.presentation.ui.components.MessageFullScreen
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@Composable
fun OrderHistoryScreen(navigateToLogin: () -> Unit) {
    OrderHistoryContent(
        navigateToLogin = navigateToLogin
    )
}

@Composable
private fun OrderHistoryContent(
    navigateToLogin: () -> Unit
) {
    Scaffold(
        topBar = {
            LPCenterTopbar(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.order_history_screen)
            )
        },
        containerColor = AppTheme.colorScheme.background
    ) { paddingValues ->

        MessageFullScreen(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            title = stringResource(R.string.not_signed_in_title),
            subtitle = stringResource(R.string.not_signed_in_subtitle),
            buttonText = stringResource(R.string.login_button)
        ) {
            navigateToLogin()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderHistoryScreenContentPreview() {
    AppTheme {
        OrderHistoryContent(
            navigateToLogin = {}
        )
    }
}