package com.apjake.compose_canvas.features.basic_canvas

import android.graphics.Paint
import android.graphics.Paint.Style
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import com.apjake.compose_canvas.common.base.ComposeActivity

class BasicCanvasActivity : ComposeActivity() {
    override fun renderView(): @Composable () -> Unit = {
        BasicCanvasScreen()
    }
}

@Composable
fun BasicCanvasScreen() {
    Canvas(
        modifier = Modifier
            .padding(20.dp)
            .width(200.dp)
            .height(200.dp)
    ) {
        drawRect(
            color = Color.Yellow,
            topLeft = Offset(50f, 50f),
            size = Size(350f, 100f)
        )

        drawContext.canvas.nativeCanvas.apply {
            drawText(
                "Hello Canvas",
                50f,
                50f,
                Paint().apply {
                    color = android.graphics.Color.RED
                    textSize = 40f
                    style = Style.FILL_AND_STROKE
                }
            )
        }
    }
}
