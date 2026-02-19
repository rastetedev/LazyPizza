package com.raulastete.lazypizza.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Primary = Color(0xFFF36B50)
val Primary8 = Color(0xFFF36B50).copy(alpha = 0.08f)
val Warning = Color(0xFFF9A825)
val Success = Color(0xFF2E7D32)
val Outline = Color(0xFFE6E7ED)
val Outline50 = Color(0xFFE6E7ED).copy(alpha = 0.5f)
val Background = Color(0xFFFAFBFC)
val TextPrimary = Color(0xFF03131F)
val TextPrimary8 = Color(0xFF03131F).copy(alpha = 0.08f)
val TextSecondary = Color(0xFF627686)
val TextSecondary8 = Color(0xFF627686).copy(alpha = 0.08f)
val TextOnPrimary = Color(0xFFFFFFFF)
val TextOnPrimary12 = Color(0xFFFFFFFF).copy(alpha = 0.12f)
val SurfaceHigher = Color(0xFFFFFFFF)
val SurfaceHighest = Color(0xFFF0F3F6)
val Overlay = Color(0xFF03131F).copy(alpha = 0.6f)
val PrimaryGradient = Brush.verticalGradient(
    colors = listOf(Primary, Color(0xFFF9966F))
)