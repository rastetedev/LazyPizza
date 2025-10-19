package com.raulastete.lazypizza.presentation.menu.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raulastete.lazypizza.presentation.ui.components.Skeleton

@Composable
fun MenuSkeleton() {
    Column(Modifier
        .padding(16.dp)
        .statusBarsPadding()) {

        Skeleton(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp), shape = RoundedCornerShape(28.dp)
        )

        Spacer(Modifier.height(16.dp))

        Skeleton(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp), shape = RoundedCornerShape(28.dp)
        )

        Spacer(Modifier.height(16.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(4) {
                Skeleton(
                    modifier = Modifier
                        .width(80.dp)
                        .height(40.dp), shape = RoundedCornerShape(8.dp)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
                Skeleton(
                    modifier = Modifier
                        .width(80.dp)
                        .height(40.dp),
                    shape = RoundedCornerShape(8.dp)
                )
            }
            items(6) {
                Skeleton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    shape = RoundedCornerShape(8.dp)
                )
            }

        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun MenuSkeletonPreview() {
    MenuSkeleton()
}