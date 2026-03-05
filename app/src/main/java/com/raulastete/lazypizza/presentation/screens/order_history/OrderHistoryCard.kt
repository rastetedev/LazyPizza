package com.raulastete.lazypizza.presentation.screens.order_history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.R
import com.raulastete.lazypizza.presentation.theme.LazyPizzaTheme
import com.raulastete.lazypizza.presentation.theme.Success
import com.raulastete.lazypizza.presentation.theme.Warning
import com.raulastete.lazypizza.presentation.theme.body4Regular
import com.raulastete.lazypizza.presentation.theme.label3Semibold
import com.raulastete.lazypizza.presentation.theme.title3

data class OrderHistoryDetails(
    val orderNumber: String,
    val orderDate: String,
    val totalPrice: String,
    val items: List<String>,
    val status: OrderStatus,
)

@Composable
fun OrderHistoryCard(
    details: OrderHistoryDetails,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = details.orderNumber,
                    style = MaterialTheme.typography.title3
                )
                Text(
                    text = details.orderDate,
                    style = MaterialTheme.typography.body4Regular.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Spacer(Modifier.height(16.dp))
                details.items.forEach {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.body4Regular
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                OrderStatusIndicator(status = details.status)
                Spacer(Modifier.weight(1f))
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = stringResource(R.string.total_amount_label),
                        style = MaterialTheme.typography.body4Regular.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    Text(
                        text = details.totalPrice,
                        style = MaterialTheme.typography.title3
                    )
                }
            }
        }
    }
}

@Composable
private fun OrderStatusIndicator(status: OrderStatus) {
    Box(
        Modifier
            .background(
                color = status.color,
                shape = RoundedCornerShape(50)
            )
            .padding(vertical = 2.dp, horizontal = 8.dp)
    ) {
        Text(
            text = stringResource(status.nameResource),
            style = MaterialTheme.typography.label3Semibold.copy(
                color = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}

enum class OrderStatus(val nameResource: Int, val color: Color) {
    IN_PROGRESS(R.string.status_in_progress, Warning),
    COMPLETED(R.string.status_completed, Success)
}

@Composable
@Preview
private fun OrderHistoryCardPreview() {
    LazyPizzaTheme {
        OrderHistoryCard(
            details = OrderHistoryDetails(
                orderNumber = "Order #123456",
                orderDate = "September 25, 12:15",
                totalPrice = "$24.90",
                items = listOf(
                    "1 x Margherita",
                    "2 x Pepsi",
                    "2 x Cookies Ice Cream"
                ),
                status = OrderStatus.IN_PROGRESS
            )
        )
    }
}