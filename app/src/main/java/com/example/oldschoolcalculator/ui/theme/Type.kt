package com.example.oldschoolcalculator.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import com.example.oldschoolcalculator.R

val creato = FontFamily(Font(R.font.creato_display_black))
val digitalDisplay = FontFamily(Font(R.font.digital_display))

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = creato,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp
    ),

)
