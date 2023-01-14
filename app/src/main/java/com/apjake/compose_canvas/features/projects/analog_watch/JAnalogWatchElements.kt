package com.apjake.compose_canvas.features.projects.analog_watch

import android.graphics.Paint
import android.graphics.Paint.Align.CENTER
import android.graphics.Paint.Style.FILL_AND_STROKE
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.withRotation
import com.apjake.compose_canvas.common.ext.asDegree
import com.apjake.compose_canvas.common.ext.asRadian
import com.apjake.compose_canvas.common.ext.withZeroLeading
import kotlin.math.cos
import kotlin.math.sin

/**
 * Created by AP-Jake
 * on Jan 15, 2023
 */

@Composable
fun JAnalogWatch(
    modifier: Modifier = Modifier,
    seconds: Float = 0f,
    minutes: Float = 0f,
    hours: Float = 0f,
    currentTime: String = "06:30 PM",
    radius: Dp = 140.dp,
    displayHourTextSize: TextUnit = 12.sp,
    primaryColor: Color = Color(0xFF00CCFF),
) {

    val hourHandLength = 45.dp
    val minuteHandLength = 80.dp
    val secondHandLength = 120.dp
    val innerRadius = radius - 30.dp
    val backgroundColor = android.graphics.Color.parseColor("#FF151516")

    Canvas(modifier = modifier.size(radius * 2f)) {
        drawContext.canvas.nativeCanvas.apply {
            drawCircle(
                center.x,
                center.y,
                radius.toPx(),
                Paint().apply {
                    color = backgroundColor
                    setShadowLayer(
                        30f,
                        0f,
                        0f,
                        android.graphics.Color.argb(100, 0, 0, 0)
                    )
                }
            )
        }

        drawCircle(
            color = primaryColor,
            radius = (radius - 20.dp).toPx(),
            center = center,
            style = Stroke(
                width = 1.dp.toPx(),
                cap = StrokeCap.Round
            )
        )

        drawContext.canvas.nativeCanvas.apply {
            drawText(
                "JAKE",
                center.x + innerRadius.toPx() / 3,
                center.y - innerRadius.toPx() / 3,
                Paint().apply {
                    this.textSize = 12.sp.toPx()
                    this.color = android.graphics.Color.parseColor("#FFA8A8A8")
                    this.textAlign = CENTER
                    this.typeface = Typeface.MONOSPACE
                    this.letterSpacing = .5f
                }
            )
            drawText(
                currentTime,
                center.x,
                center.y + innerRadius.toPx() / 2.5f,
                Paint().apply {
                    this.textSize = 12.sp.toPx()
                    this.textAlign = CENTER
                    this.typeface = Typeface.MONOSPACE
                    this.color = android.graphics.Color.WHITE
                    this.letterSpacing = .3f
                }
            )
        }

        for (i in 0..59) {
            val angleInRad = (i * (360f / 60f)).asRadian
            val lineLength = if (i % 5 == 0) 18.dp.toPx() else 12.dp.toPx()
            val lineStroke =
                if (i % 5 == 0) 4.dp.toPx()
                else .5.dp.toPx()
            val color =
                if (i % 5 == 0) androidx.compose.ui.graphics.Color.White else Color(0xFF9C9C9C)

            val lineStart = Offset(
                x = innerRadius.toPx() * cos(angleInRad) + center.x,
                y = innerRadius.toPx() * sin(angleInRad) + center.y
            )
            val lineEnd = Offset(
                x = (innerRadius.toPx() - lineLength) * cos(angleInRad) + center.x,
                y = (innerRadius.toPx() - lineLength) * sin(angleInRad) + center.y
            )

            if (i % 5 == 0) {
                val textRadius = innerRadius.toPx() + displayHourTextSize.toPx() / 2
                val x = textRadius * cos(angleInRad) + center.x
                val y = textRadius * sin(angleInRad) + center.y

                val hourI = i + 15
                val hourText =
                    if (hourI > 60) (hourI % 60).withZeroLeading(2) else hourI.withZeroLeading(2)
                val blockSize = Size(
                    width = displayHourTextSize.toPx() * 2,
                    height = displayHourTextSize.toPx()
                )
                drawContext.canvas.nativeCanvas.apply {
                    withRotation(
                        degrees = angleInRad.asDegree + 90,
                        pivotX = x,
                        pivotY = y
                    ) {
                        drawRect(
                            color = Color(backgroundColor),
                            topLeft = Offset(
                                x = x - blockSize.width / 2,
                                y = y - blockSize.height + 5
                            ),
                            size = blockSize
                        )
                        drawText(
                            hourText,
                            x,
                            y,
                            Paint().apply {
                                this.textSize = displayHourTextSize.toPx()
                                this.style = FILL_AND_STROKE
                                this.textAlign = CENTER
                                this.color = android.graphics.Color.WHITE
                            },
                        )
                    }
                }
            }
            drawLine(
                start = lineStart,
                end = lineEnd,
                color = color,
                strokeWidth = lineStroke
            )

        }

        // hours
        rotate(
            degrees = hours * (360f / 12f)
        ) {
            val path = Path().apply {
                moveTo(center.x, center.y - hourHandLength.toPx())
                lineTo(center.x - 8.dp.toPx(), center.y)
                lineTo(center.x, center.y + 20.dp.toPx())
                lineTo(center.x + 8.dp.toPx(), center.y)
                lineTo(center.x, center.y - hourHandLength.toPx())
            }
            drawPath(
                path = path,
                color = primaryColor
            )

            val secPath = Path().apply {
                moveTo(center.x, center.y - (hourHandLength.toPx() / 2))
                lineTo(center.x - 4.dp.toPx(), center.y)
                lineTo(center.x, center.y + 12.dp.toPx())
                lineTo(center.x + 4.dp.toPx(), center.y)
                lineTo(center.x, center.y - (hourHandLength.toPx() / 2))
            }
            drawPath(
                path = secPath,
                color = Color(backgroundColor)
            )
        }

        // minutes
        rotate(
            degrees = minutes * (360f / 60f)
        ) {
            val path = Path().apply {
                moveTo(center.x, center.y - minuteHandLength.toPx())
                lineTo(center.x - 8.dp.toPx(), center.y)
                lineTo(center.x, center.y + 20.dp.toPx())
                lineTo(center.x + 8.dp.toPx(), center.y)
                lineTo(center.x, center.y - minuteHandLength.toPx())
            }
            drawPath(
                path = path,
                color = primaryColor
            )

            val secPath = Path().apply {
                moveTo(center.x, center.y - (minuteHandLength.toPx() / 2))
                lineTo(center.x - 4.dp.toPx(), center.y)
                lineTo(center.x, center.y + 12.dp.toPx())
                lineTo(center.x + 4.dp.toPx(), center.y)
                lineTo(center.x, center.y - (minuteHandLength.toPx() / 2))
            }
            drawPath(
                path = secPath,
                color = Color(backgroundColor)
            )
        }

        // seconds
        rotate(
            degrees = seconds * (360f / 60f)
        ) {
            val path = Path().apply {
                moveTo(center.x, center.y - secondHandLength.toPx())
                lineTo(center.x - 3.dp.toPx(), center.y)
                lineTo(center.x, center.y + 26.dp.toPx())
                lineTo(center.x + 3.dp.toPx(), center.y)
                lineTo(center.x, center.y - secondHandLength.toPx())
            }
            drawPath(
                path = path,
                color = Color(0xFFDD1155)
            )
        }

        drawCircle(
            color = Color.Black,
            center = center,
            radius = 5.dp.toPx()
        )

    }

}