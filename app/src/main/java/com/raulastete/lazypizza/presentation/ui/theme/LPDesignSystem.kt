package com.raulastete.lazypizza.presentation.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import com.raulastete.lazypizza.presentation.ui.DeviceMode

data class LPColorScheme(
    val textPrimary: Color,
    val textSecondary: Color,
    val textSecondary8: Color,
    val textOnPrimary: Color,
    val background: Color,
    val surfaceHigher: Color,
    val surfaceHighest: Color,
    val outline: Color,
    val outline50: Color,
    val primaryGradient: Brush,
    val primary: Color,
    val primary8: Color,
)

data class LPTypography(
    val title1Semibold: TextStyle,
    val title1Medium: TextStyle,
    val title2: TextStyle,
    val title3: TextStyle,
    val label2Semibold: TextStyle,
    val body1Regular: TextStyle,
    val body1Medium: TextStyle,
    val body3Regular: TextStyle,
    val body3Medium: TextStyle,
    val body3Bold: TextStyle,
    val body4Regular: TextStyle,
)

data class LPShape(
    val card: Shape,
    val button: Shape,
    val small: Shape
)

val LocalAppColorScheme = staticCompositionLocalOf {
    LPColorScheme(
        textPrimary = Color.Unspecified,
        textSecondary = Color.Unspecified,
        textSecondary8 = Color.Unspecified,
        textOnPrimary = Color.Unspecified,
        background = Color.Unspecified,
        surfaceHigher = Color.Unspecified,
        surfaceHighest = Color.Unspecified,
        outline = Color.Unspecified,
        outline50 = Color.Unspecified,
        primaryGradient = Brush.linearGradient(listOf(Color.Unspecified, Color.Unspecified)),
        primary = Color.Unspecified,
        primary8 = Color.Unspecified,
    )
}

val LocalAppTypography = staticCompositionLocalOf {
    LPTypography(
        title1Semibold = TextStyle(),
        title1Medium = TextStyle(),
        title2 = TextStyle(),
        title3 = TextStyle(),
        label2Semibold = TextStyle(),
        body1Regular = TextStyle(),
        body1Medium = TextStyle(),
        body3Regular = TextStyle(),
        body3Medium = TextStyle(),
        body3Bold = TextStyle(),
        body4Regular = TextStyle(),
    )
}

val LocalAppShape = staticCompositionLocalOf {
    LPShape(
        card = RectangleShape,
        button = RectangleShape,
        small = RectangleShape
    )
}

val LocalDeviceMode = staticCompositionLocalOf<DeviceMode> { error("No DeviceMode provided") }