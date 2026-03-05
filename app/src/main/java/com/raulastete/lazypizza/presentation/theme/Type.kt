package com.raulastete.lazypizza.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.raulastete.lazypizza.R

val instrumentSans = FontFamily(
    Font(R.font.instrument_regular, FontWeight.Normal),
    Font(R.font.instrument_medium, FontWeight.Medium),
    Font(R.font.instrument_semibold, FontWeight.SemiBold),
    Font(R.font.instrument_bold, FontWeight.Bold)
)

val Typography = Typography()


val Typography.title1Medium: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = instrumentSans,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 28.sp
    )

val Typography.title1Semibold: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = instrumentSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 28.sp
    )

val Typography.title2: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = instrumentSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 24.sp
    )

val Typography.title3: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = instrumentSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 22.sp
    )

val Typography.title4: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = instrumentSans,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp
    )

val Typography.label1Medium: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = instrumentSans,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )

val Typography.label1Semibold: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = instrumentSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )

val Typography.label2Medium: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = instrumentSans,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp
    )

val Typography.label2Semibold: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = instrumentSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp
    )

val Typography.label3Semibold: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = instrumentSans,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp
    )

val Typography.body1Regular: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = instrumentSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 22.sp
    )

val Typography.body1Medium: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = instrumentSans,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 22.sp
    )

val Typography.body2Regular: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = instrumentSans,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 22.sp
    )

val Typography.body3Regular: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = instrumentSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.sp
    )

val Typography.body3Medium: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = instrumentSans,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 18.sp
    )

val Typography.body3Bold: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = instrumentSans,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 18.sp
    )

val Typography.body4Regular: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = instrumentSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp
    )
