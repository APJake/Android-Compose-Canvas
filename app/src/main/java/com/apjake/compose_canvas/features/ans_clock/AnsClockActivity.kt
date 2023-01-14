package com.apjake.compose_canvas.features.ans_clock

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion
import androidx.compose.ui.Modifier
import com.apjake.compose_canvas.common.base.ComposeActivity
import kotlinx.coroutines.delay

/**
 * Created by AP-Jake
 * on Jan 14, 2023
 */
class AnsClockActivity : ComposeActivity() {

    override fun renderView(): @Composable () -> Unit = {

        val milliSeconds = remember {
            System.currentTimeMillis()
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

        LaunchedEffect(key1 = seconds) {
            delay(1000)
            minutes += 1f / 60f
            hours += 1f / (60f * 60f * 12f)
            seconds += 1f
        }

        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            Clock(
                seconds = seconds,
                minutes = minutes,
                hours = hours
            )

        }

    }

}