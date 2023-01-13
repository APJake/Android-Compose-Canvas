package com.apjake.compose_canvas.features.weight_picker

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ScaleStyle(
    val scaleWidth: Dp = 100.dp,
    val radius: Dp = 550.dp,
    val speed: Float = 1f,
    val normalLineColor: Color = Color.LightGray,
    val fiveStepLineColor: Color = Color.Blue,
    val tenStepLineColor: Color = Color.Black,
    val normalLineLength: Dp = 15.dp,
    val fiveStepLineLength: Dp = 25.dp,
    val tenStepLineLength: Dp = 35.dp,
    val scaleIndicatorColor: Color = Color.Blue,
    val scaleIndicatorLength: Dp = 60.dp,
    val textSize: TextUnit = 18.sp,
)
