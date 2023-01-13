package com.apjake.compose_canvas.features.asm_clock

import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.apjake.compose_canvas.common.base.ComposeActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**
 * Created by AP-Jake
 * on Jan 14, 2023
 */
class AsmClockActivity : ComposeActivity() {

    private val state = MutableStateFlow(ClockMaterial())
    private var job: Job? = null

    private fun runClock() {
        job = lifecycleScope.launchWhenStarted {
            delay(1000)
            state.update {
                it.tick()
            }
            runClock()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runClock()
    }

    override fun renderView(): @Composable () -> Unit = {

        val uiState: ClockMaterial by state.collectAsState()

        Box(modifier = Modifier.fillMaxSize()) {
            JClock(
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.Center),
                hour = uiState.hour,
                minute = uiState.minute,
                second = uiState.second
            )
        }

    }
}