package com.apjake.compose_canvas.features.asm_clock

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Created by AP-Jake
 * on Jan 14, 2023
 */
data class ClockStyle(
    val size: Dp = 200.dp,
    val normalTagLength: Dp = 12.dp,
    val primaryTagLength: Dp = 18.dp,
    val color: Color = Color.White,
    val centerColor: Color = Color.Black,
    val normalTagColor: Color = Color.LightGray,
    val primaryTagColor: Color = Color.Black,
    val secondHandColor: Color = Color.Red,
    val secondHandLength: Dp = 80.dp,
    val minuteHandColor: Color = Color.DarkGray,
    val minuteHandLength: Dp = 70.dp,
    val hourHandColor: Color = Color.Black,
    val hourHandLength: Dp = 50.dp,
)
