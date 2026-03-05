package com.raulastete.lazypizza.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.presentation.theme.LazyPizzaTheme

@Composable
fun Counter(
    count: Int,
    onClickDecreaseCountButton: () -> Unit,
    onClickIncreaseCountButton: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        SecondaryIconButton(
            onClick = onClickDecreaseCountButton,
            modifier = Modifier.size(22.dp)
        ){
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.minus_icon),
                contentDescription = null,
                modifier = Modifier.size(22.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Box(Modifier.width(52.dp), contentAlignment = Alignment.Center) {
            Text("$count", textAlign = TextAlign.Center)
        }
        SecondaryIconButton(
            onClick = onClickIncreaseCountButton,
            modifier = Modifier.size(22.dp)
        ){
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.plus_icon),
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CounterPreview() {
    LazyPizzaTheme {
        Counter(
            count = 1,
            onClickDecreaseCountButton = {},
            onClickIncreaseCountButton = {}
        )
    }
}