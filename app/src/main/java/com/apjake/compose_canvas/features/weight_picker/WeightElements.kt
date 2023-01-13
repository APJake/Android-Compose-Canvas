package com.apjake.compose_canvas.features.weight_picker

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Align.CENTER
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
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun Scale(
    modifier: Modifier = Modifier,
    scaleStyle: ScaleStyle = ScaleStyle(),
    minWeight: Int = 20,
    maxWeight: Int = 250,
    initialWeight: Int = 80,
    onWeightChange: (Int) -> Unit,
) {
    val scaleWidth = scaleStyle.scaleWidth
    val radius = scaleStyle.radius
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
                       oldAngle = angle
                    }
                ) { change, _ ->
                    val touchAngle = atan2(
                        circleCenter.x - change.position.x,
                        circleCenter.y - change.position.y
                    ).asDegree
                    val newAngle = oldAngle + ((dragStartedAngle - touchAngle) * scaleStyle.speed)
                    angle = newAngle.coerceIn(
                        // when drag to right degree will decrease
                        minimumValue = initialWeight - maxWeight.toFloat(),
                        maximumValue = initialWeight - minWeight.toFloat()
                    )
                    onWeightChange(initialWeight - angle.roundToInt())
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
                        60f,
                        0f,
                        0f,
                        Color.argb(50, 0, 0, 0)
                    )
                }
            )
        }

        // draw lines
        for (i in minWeight..maxWeight) {
            // 60 - 60 + 0 - 90 = -90 => default should be top
            val angleInRad = (i - initialWeight + angle - 90).asRadian
            val lineType = when {
                i % 10 == 0 -> TenStep
                i % 5 == 0 -> FiveStep
                else -> Normal
            }
            val lineLength = when (lineType) {
                Normal -> scaleStyle.normalLineLength.toPx()
                FiveStep -> scaleStyle.fiveStepLineLength.toPx()
                TenStep -> scaleStyle.tenStepLineLength.toPx()
            }
            val lineColor = when (lineType) {
                Normal -> scaleStyle.normalLineColor
                FiveStep -> scaleStyle.fiveStepLineColor
                TenStep -> scaleStyle.tenStepLineColor
            }
            val startPosition = Offset(
                x = cos(angleInRad) * (outerRadius - lineLength) + circleCenter.x,
                y = sin(angleInRad) * (outerRadius - lineLength) + circleCenter.y
            )
            val endPosition = Offset(
                x = cos(angleInRad) * outerRadius + circleCenter.x,
                y = sin(angleInRad) * outerRadius + circleCenter.y
            )

            drawContext.canvas.nativeCanvas.apply {
                if (lineType is TenStep) {
                    val textRadius =
                        outerRadius - lineLength - 5.dp.toPx() - scaleStyle.textSize.toPx()
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
                                textSize = scaleStyle.textSize.toPx()
                                textAlign = CENTER
                            }
                        )
                    }
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
            y = circleCenter.y - innerRadius - scaleStyle.scaleIndicatorLength.toPx()
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
            color = scaleStyle.scaleIndicatorColor
        )
    }
}
