package com.apjake.compose_canvas.features.asm_clock

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import com.apjake.compose_canvas.common.ext.asRadian
import com.apjake.compose_canvas.features.asm_clock.TagType.Normal
import com.apjake.compose_canvas.features.asm_clock.TagType.Primary
import kotlin.math.cos
import kotlin.math.sin

/**
 * Created by AP-Jake
 * on Jan 14, 2023
 */

@Composable
fun JClock(
    modifier: Modifier = Modifier,
    second: Int = 0,
    minute: Int = 0,
    hour: Int = 0,
    style: ClockStyle = ClockStyle(),
) {
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    Canvas(modifier = modifier) {
        circleCenter = this.center
        val circleRadius = style.size.toPx() / 2

        drawContext.canvas.nativeCanvas.apply {
            drawCircle(
                circleCenter.x,
                circleCenter.y,
                circleRadius,
                Paint().apply {
                    color = Color.WHITE
                    setShadowLayer(
                        80f,
                        0f,
                        0f,
                        Color.argb(20, 0, 0, 0)
                    )
                }
            )
        }

        val secondAngleInRad = (second * 6 - 90).asRadian
        val secondRadius = style.secondHandLength.toPx()
        val secondEndOffset = Offset(
            x = cos(secondAngleInRad) * secondRadius + circleCenter.x,
            y = sin(secondAngleInRad) * secondRadius + circleCenter.y
        )

        val minuteAngleInRad = (minute * 6 - 90).asRadian
        val minuteRadius = style.minuteHandLength.toPx()
        val minuteEndOffset = Offset(
            x = cos(minuteAngleInRad) * minuteRadius + circleCenter.x,
            y = sin(minuteAngleInRad) * minuteRadius + circleCenter.y
        )

        val hourAngleInRad = (hour * 30 - 90).asRadian
        val hourRadius = style.hourHandLength.toPx()
        val hourEndOffset = Offset(
            x = cos(hourAngleInRad) * hourRadius + circleCenter.x,
            y = sin(hourAngleInRad) * hourRadius + circleCenter.y
        )
        // hour hand
        drawLine(
            color = style.hourHandColor,
            start = circleCenter,
            end = hourEndOffset,
            strokeWidth = 3.dp.toPx()
        )
        // minute hand
        drawLine(
            color = style.minuteHandColor,
            start = circleCenter,
            end = minuteEndOffset,
            strokeWidth = 2.dp.toPx()
        )
        // second hand
        drawLine(
            color = style.secondHandColor,
            start = circleCenter,
            end = secondEndOffset,
            strokeWidth = 1.dp.toPx()
        )
        // just center
        drawCircle(
            color = style.centerColor,
            radius = 3.dp.toPx(),
            center = circleCenter
        )

        // drawing tags
        for (i in 0 until 60) {

            // for 1 tag is 6 degree
            val angleInRad = ((i * 6) - 90).asRadian

            val tagType = when {
                i % 5 == 0 -> Primary
                else -> Normal
            }

            val tagLength = when (tagType) {
                Normal -> style.normalTagLength.toPx()
                Primary -> style.primaryTagLength.toPx()
            }

            val tagColor = when (tagType) {
                Normal -> style.normalTagColor
                Primary -> style.primaryTagColor
            }

            val tagStroke = when (tagType) {
                Normal -> 1.dp.toPx()
                Primary -> 1.3.dp.toPx()
            }

            val startPosition = Offset(
                x = cos(angleInRad) * (circleRadius - tagLength) + circleCenter.x,
                y = sin(angleInRad) * (circleRadius - tagLength) + circleCenter.y
            )

            val endPosition = Offset(
                x = cos(angleInRad) * circleRadius + circleCenter.x,
                y = sin(angleInRad) * circleRadius + circleCenter.y
            )

            drawLine(
                color = tagColor,
                start = startPosition,
                end = endPosition,
                strokeWidth = tagStroke
            )

        }

    }
}
