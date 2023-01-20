package com.apjake.compose_canvas.features.projects.card_slider

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Created by AP-Jake
 * on Jan 20, 2023
 */

data class JSliderStyle(
    val scaleWidth: Dp = 100.dp,
    val elevation: Dp = 20.dp,
    val radius: Dp = 550.dp,
    val speed: Float = 1f,
    val lineColor: Color = Color.LightGray,
    val lineLength: Dp = 30.dp,
    val scaleIndicatorColor: Color = Color.Blue,
    val scaleIndicatorLength: Dp = 60.dp,
    val textSize: TextUnit = 18.sp,
)
