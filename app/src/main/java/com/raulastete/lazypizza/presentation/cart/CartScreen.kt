package com.raulastete.lazypizza.presentation.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.presentation.ui.components.LPCenterTopbar
import com.raulastete.lazypizza.presentation.ui.components.LPPrimaryButton
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
        }
    ) { paddingValues ->

        EmptyCartContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            navigateToMenu = navigateToMenu
        )
    }
}

@Composable
private fun EmptyCartContent(modifier: Modifier = Modifier, navigateToMenu: () -> Unit) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        Text(stringResource(R.string.cart_empty_title), style = AppTheme.typography.title1Medium)
        Text(stringResource(R.string.cart_empty_subtitle), style = AppTheme.typography.body3Regular, color = AppTheme.colorScheme.textSecondary)
        Spacer(Modifier.height(20.dp))
        LPPrimaryButton(text = stringResource(R.string.cart_empty_button)) {
            navigateToMenu()
        }
        Spacer(Modifier.weight(2f))
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