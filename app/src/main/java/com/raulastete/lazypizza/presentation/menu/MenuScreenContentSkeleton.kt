package com.raulastete.lazypizza.presentation.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.presentation.ui.components.ShimmerContainer
import com.raulastete.lazypizza.presentation.ui.components.Skeleton
import com.raulastete.lazypizza.presentation.ui.theme.AppTheme

@Composable
fun MenuScreenContentSkeleton() {

    ShimmerContainer { brush ->
        Column(
            Modifier
                .background(AppTheme.colorScheme.background)
                .padding(16.dp)
                .statusBarsPadding()
        ) {

            Skeleton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(28.dp),
                brush = brush
            )

            Spacer(Modifier.height(16.dp))

            Skeleton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(28.dp),
                brush = brush
            )

            Spacer(Modifier.height(16.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(4) {
                    Skeleton(
                        modifier = Modifier
                            .width(80.dp)
                            .height(40.dp),
                        shape = RoundedCornerShape(8.dp),
                        brush = brush
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Skeleton(
                modifier = Modifier
                    .width(80.dp)
                    .height(40.dp),
                shape = RoundedCornerShape(8.dp),
                brush = brush
            )

            repeat(3) {
                Spacer(Modifier.height(8.dp))

                Skeleton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    shape = RoundedCornerShape(8.dp),
                    brush = brush
                )
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun MenuSkeletonPreview() {
    MenuScreenContentSkeleton()
}