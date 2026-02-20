package com.raulastete.lazypizza.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.ui.theme.Primary8
import com.raulastete.lazypizza.ui.theme.title3

@Composable
fun SecondaryTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(percent = 50),
        border = BorderStroke(
            width = 1.dp,
            color = Primary8
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.title3,
            color = MaterialTheme.colorScheme.primary
        )
    }
}