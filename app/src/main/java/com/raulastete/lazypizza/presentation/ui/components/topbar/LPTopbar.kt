package com.raulastete.lazypizza.presentation.ui.components.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LPTopbar(
    modifier: Modifier
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppTheme.colorScheme.background
        ),
        modifier = modifier,
        title = {
            AppLogo()
        },
        actions = {
            StorePhoneNumber()
        }
    )
}

@Composable
private fun AppLogo() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_pizza),
            contentDescription = "App Logo",
            tint = Color.Unspecified
        )
        Text(
            text = "LazyPizza",
            style = AppTheme.typography.body3Bold
        )
    }
}

@Composable
private fun StorePhoneNumber() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_phone),
            contentDescription = "Store Phone Number",
            tint = Color.Unspecified
        )
        Text(
            text = "+1 (555) 321-7890",
            style = AppTheme.typography.body1Regular
        )
    }
}

@Preview
@Composable
private fun LPTopbarPreview() {
    AppTheme {
        LPTopbar(modifier = Modifier.fillMaxWidth())
    }
}