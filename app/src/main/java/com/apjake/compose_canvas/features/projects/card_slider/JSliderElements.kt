package com.apjake.compose_canvas.features.projects.card_slider

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Align.CENTER
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.core.graphics.withRotation
import com.apjake.compose_canvas.common.ext.asDegree
import com.apjake.compose_canvas.common.ext.asRadian
import com.apjake.compose_canvas.features.weight_picker.LineType.FiveStep
import com.apjake.compose_canvas.features.weight_picker.LineType.Normal
import com.apjake.compose_canvas.features.weight_picker.LineType.TenStep
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun JSlider(
    modifier: Modifier = Modifier,
    sliderStyle: JSliderStyle = JSliderStyle(),
    minValue: Int = 1,
    maxValue: Int = 30,
    initialValue: Int = 10,
    onValueChanged: (Int) -> Unit,
) {
    val scaleWidth = sliderStyle.scaleWidth
    val radius = sliderStyle.radius
    var center by remember {
        mutableStateOf(Offset.Zero)
    }
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }
    var angle by remember {
        mutableStateOf(0f)
    }
    var dragStartedAngle by remember {
        mutableStateOf(0f)
    }
    var oldAngle by remember {
        mutableStateOf(angle)
    }

    Canvas(
        modifier = modifier
            .pointerInput(true) {
                detectDragGestures(
                    onDragStart = { offset ->
                        dragStartedAngle = atan2(
                            circleCenter.x - offset.x,
                            circleCenter.y - offset.y
                        ).asDegree
                    },
                    onDragEnd = {
                        // snappy
                        angle = (angle / 10f).roundToInt() * 10f
                        oldAngle = angle
                    }
                ) { change, _ ->
                    val touchAngle = atan2(
                        circleCenter.x - change.position.x,
                        circleCenter.y - change.position.y
                    ).asDegree
                    val newAngle = oldAngle + ((dragStartedAngle - touchAngle) * sliderStyle.speed)
                    angle = newAngle.coerceIn(
                        // when drag to right degree will decrease
                        minimumValue = initialValue - maxValue.toFloat(),
                        maximumValue = initialValue - minValue.toFloat()
                    )
                    onValueChanged(initialValue - angle.roundToInt())
                }
            }
    ) {
        center = this.center
        circleCenter = Offset(
            x = center.x,
            y = scaleWidth.toPx() / 2 + radius.toPx()
        )
        val outerRadius = radius.toPx() + scaleWidth.toPx() / 2
        val innerRadius = radius.toPx() - scaleWidth.toPx() / 2

        drawContext.canvas.nativeCanvas.apply {
            drawCircle(
                circleCenter.x,
                circleCenter.y,
                radius.toPx(),
                Paint().apply {
                    strokeWidth = scaleWidth.toPx()
                    color = Color.WHITE
                    style = Paint.Style.STROKE
                    setShadowLayer(
                        sliderStyle.elevation.toPx(),
                        0f,
                        0f,
                        Color.argb(50, 0, 0, 0)
                    )
                }
            )
        }

        // draw lines
        for (i in minValue..maxValue step 10) {
            // 60 - 60 + 0 - 90 = -90 => default should be top
            val angleInRad = (i - initialValue + angle - 90).asRadian
            val lineLength = sliderStyle.lineLength.toPx()
            val lineColor = sliderStyle.lineColor
            val startPosition = Offset(
                x = cos(angleInRad) * (outerRadius - lineLength) + circleCenter.x,
                y = sin(angleInRad) * (outerRadius - lineLength) + circleCenter.y
            )
            val endPosition = Offset(
                x = cos(angleInRad) * outerRadius + circleCenter.x,
                y = sin(angleInRad) * outerRadius + circleCenter.y
            )

            drawContext.canvas.nativeCanvas.apply {
                val textRadius =
                    outerRadius - lineLength - 5.dp.toPx() - sliderStyle.textSize.toPx()
                val x = textRadius * cos(angleInRad) + circleCenter.x
                val y = textRadius * sin(angleInRad) + circleCenter.y
                withRotation(
                    degrees = angleInRad.asDegree + 90f,
                    pivotX = x,
                    pivotY = y
                ) {
                    drawText(
                        abs(i).toString(),
                        x,
                        y,
                        Paint().apply {
                            textSize = sliderStyle.textSize.toPx()
                            textAlign = CENTER
                        }
                    )
                }
            }

            drawLine(
                start = startPosition,
                end = endPosition,
                color = lineColor,
                strokeWidth = 1.dp.toPx()
            )
        }

        val middleTop = Offset(
            x = circleCenter.x,
            y = circleCenter.y - innerRadius - sliderStyle.scaleIndicatorLength.toPx()
            // bcz it's start from top
        )
        val bottomLeft = Offset(
            x = circleCenter.x - 4f,
            y = circleCenter.y - innerRadius
        )
        val bottomRight = Offset(
            x = circleCenter.x + 4f,
            y = circleCenter.y - innerRadius
        )
        val indicator = Path().apply {
            moveTo(middleTop.x, middleTop.y)
            lineTo(bottomLeft.x, bottomLeft.y)
            lineTo(bottomRight.x, bottomRight.y)
            lineTo(middleTop.x, middleTop.y)
        }
        drawPath(
            path = indicator,
            color = sliderStyle.scaleIndicatorColor
        )
    }
}
