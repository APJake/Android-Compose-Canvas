package com.apjake.compose_canvas.features.star_clicker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apjake.compose_canvas.common.base.ComposeActivity
import kotlinx.coroutines.delay
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.random.Random

class StarClickerActivity : ComposeActivity() {

    override fun renderView(): @Composable () -> Unit = {
        Surface(modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background) {
            StarClickerScreen()
        }
    }
}

@Composable
fun StarClickerScreen() {
    var score by remember {
        mutableStateOf(0)
    }
    var isActive by remember {
        mutableStateOf(false)
    }
    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Your score: $score",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Magenta
                )
            )
            Box(modifier = Modifier.weight(1f)) {
                Button(
                    onClick = {
                        if (!isActive) score = 0
                        isActive = !isActive
                    },
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(if (isActive) "Reset" else "Start")
                }
            }
            CountDownTimer(
                startTime = 30000,
                isActive = isActive
            ) {
                isActive = false
            }
        }
        StarClicker(
            enabled = isActive
        ) {
            score++
        }
    }
}

@Composable
fun CountDownTimer(
    startTime: Long = 30000,
    isActive: Boolean = false,
    onFinish: () -> Unit = {},
) {
    var currentTime by remember {
        mutableStateOf(startTime)
    }

    LaunchedEffect(key1 = currentTime, key2 = isActive) {
        if (!isActive) {
            currentTime = startTime
            return@LaunchedEffect
        }

        if (currentTime > 0) {
            delay(1000)
            currentTime -= 1000
        } else {
            currentTime = startTime
            onFinish()
        }
    }

    Text(
        text = (currentTime / 1000).toString(),
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Green
        )
    )

}

@Composable
fun StarClicker(
    color: Color = Color.Cyan,
    size: Float = 100f,
    enabled: Boolean = false,
    onTap: () -> Unit = {},
) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        var position by remember {
            mutableStateOf(randomOffset(
                size = size,
                width = constraints.maxWidth,
                height = constraints.maxHeight
            ))
        }
        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(enabled) {
                if (!enabled) {
                    return@pointerInput
                }
                detectTapGestures {
                    val distance = sqrt(
                        (it.x - position.x).pow(2) +
                                (it.y - position.y).pow(2)
                    )
                    if (distance < size) {
                        position = randomOffset(size, constraints.maxWidth, constraints.maxHeight)
                        onTap()
                    }
                }
            }) {
            drawCircle(
                color = color,
                radius = size,
                center = position
            )
        }
    }
}

private fun randomOffset(
    size: Float,
    width: Int,
    height: Int,
): Offset {
    return Offset(
        x = Random.nextInt(size.roundToInt(), width - size.roundToInt()).toFloat(),
        y = Random.nextInt(size.roundToInt(), height - size.roundToInt()).toFloat()
    )
}