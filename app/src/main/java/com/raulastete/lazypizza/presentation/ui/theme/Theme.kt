package com.raulastete.lazypizza.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val lightColorSchema = LPColorScheme(
    textPrimary = VeryDarkNavy,
    textSecondary = MediumDesaturatedBlue,
    textSecondary8 = MediumDesaturatedBlue.copy(0.08f),
    textOnPrimary = White,
    background = LightestGray,
    surfaceHigher = White,
    surfaceHighest = PlatinumWhite,
    outline = WhisperGray,
    outline50 = WhisperGray.copy(alpha = 0.5f),
    primaryGradient = Brush.linearGradient(colors = listOf(Orange, OrangeLight)),
    primary = Orange,
    primary8 = Orange.copy(alpha = 0.08f)
)

private val typography = LPTypography(
    title1Semibold = TextStyle(
        fontFamily = InstrumentSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 28.sp
    ),
    title1Medium = TextStyle(
        fontFamily = InstrumentSans,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 28.sp
    ),
    title2 = TextStyle(
        fontFamily = InstrumentSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 24.sp
    ),
    title3 = TextStyle(
        fontFamily = InstrumentSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 22.sp
    ),
    title4 = TextStyle(
        fontFamily = InstrumentSans,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp
    ),
    label2Semibold = TextStyle(
        fontFamily = InstrumentSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    body1Regular = TextStyle(
        fontFamily = InstrumentSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    body1Medium = TextStyle(
        fontFamily = InstrumentSans,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    body3Regular = TextStyle(
        fontFamily = InstrumentSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),
    body3Medium = TextStyle(
        fontFamily = InstrumentSans,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),
    body3Bold = TextStyle(
        fontFamily = InstrumentSans,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),
    body4Regular = TextStyle(
        fontFamily = InstrumentSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
)

private val shape = LPShape(
    card = RoundedCornerShape(12.dp),
    button = RoundedCornerShape(100.dp),
    small = RoundedCornerShape(8.dp)
)

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        isDarkTheme -> lightColorSchema // TODO: Add dark theme
        else -> lightColorSchema
    }

    CompositionLocalProvider(
        LocalAppColorScheme provides colorScheme,
        LocalAppTypography provides typography,
        LocalAppShape provides shape,
        content = content
    )
}

object AppTheme {

    val colorScheme: LPColorScheme
        @Composable
        get() = LocalAppColorScheme.current

    val typography: LPTypography
        @Composable
        get() = LocalAppTypography.current

    val shape: LPShape
        @Composable
        get() = LocalAppShape.current
}