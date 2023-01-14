package com.apjake.compose_canvas.features.projects.analog_watch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.apjake.compose_canvas.common.base.ComposeActivity
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Created by AP-Jake
 * on Jan 15, 2023
 */

class JAnalogWatchActivity : ComposeActivity() {

    private val dateFormatter = SimpleDateFormat("HH:mm a", Locale.getDefault())

    override fun renderView(): @Composable () -> Unit = {

        val calendar = remember {
            Calendar.getInstance()
        }
        val milliSeconds = remember {
            calendar.timeInMillis
        }

        var seconds by remember {
            mutableStateOf((milliSeconds / 1000f) % 60f)
        }
        var minutes by remember {
            mutableStateOf((((milliSeconds / 1000f) / 60f) + 30f) % 60f)
        }
        var hours by remember {
            mutableStateOf((milliSeconds / 1000f) / 3600f + 6f)
        }

        var currentTime by remember {
            mutableStateOf(dateFormatter.format(milliSeconds))
        }

        LaunchedEffect(key1 = seconds) {
            delay(1000)
            minutes = (minutes + 1f / 60f) % 60f
            hours = (hours + 1f / (60f * 60f * 12f)) % 12f
            seconds = (seconds + 1f) % 60f
            currentTime = dateFormatter.format(milliSeconds)
        }

        Box(Modifier
            .fillMaxSize()
            .background(Color(0xFF242427)),
            contentAlignment = Alignment.Center) {

            JAnalogWatch(
                seconds = seconds,
                minutes = minutes,
                hours = hours,
                currentTime = currentTime
            )

        }

    }
}
