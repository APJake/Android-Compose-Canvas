package com.apjake.compose_canvas.common.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.apjake.compose_canvas.ui.theme.ComposeCanvasTheme

abstract class ComposeActivity : ComponentActivity() {

    private lateinit var viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val composable = renderView()
        setContent {
            ComposeCanvasTheme {
                composable()
            }
        }
    }

    @Stable
    abstract fun renderView(): @Composable () -> Unit


}