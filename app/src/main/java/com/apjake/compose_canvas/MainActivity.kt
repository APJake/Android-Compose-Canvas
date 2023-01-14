package com.apjake.compose_canvas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apjake.compose_canvas.common.base.ComposeActivity
import com.apjake.compose_canvas.common.ext.goTo
import com.apjake.compose_canvas.features.ans_clock.AnsClockActivity
import com.apjake.compose_canvas.features.asm_clock.AsmClockActivity
import com.apjake.compose_canvas.features.basic_canvas.BasicCanvasActivity
import com.apjake.compose_canvas.features.projects.analog_watch.JAnalogWatch
import com.apjake.compose_canvas.features.projects.analog_watch.JAnalogWatchActivity
import com.apjake.compose_canvas.features.star_clicker.StarClickerActivity
import com.apjake.compose_canvas.features.weight_picker.WeightPickerActivity

class MainActivity : ComposeActivity() {

    override fun renderView(): @Composable () -> Unit = {
        Surface(modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background) {
            AppFunctions(
                listOf(
                    AppFunctionModel(
                        title = "Test Screen",
                        onClick = {
                            goTo(StarClickerActivity::class.java)
                        }
                    ),
                    AppFunctionModel(
                        title = "Star Clicker",
                        onClick = {
                            goTo(StarClickerActivity::class.java)
                        }
                    ),
                    AppFunctionModel(
                        title = "Drawing on canvas",
                        onClick = {
                            goTo(BasicCanvasActivity::class.java)
                        }
                    ),
                    AppFunctionModel(
                        title = "Weight picker",
                        onClick = {
                            goTo(WeightPickerActivity::class.java)
                        }
                    ),
                    AppFunctionModel(
                        title = "Ex - 1 (Clock)",
                        onClick = {
                            goTo(AsmClockActivity::class.java)
                        }
                    ),
                    AppFunctionModel(
                        title = "Ex -1 (Clock) (solution)",
                        onClick = {
                            goTo(AnsClockActivity::class.java)
                        }
                    ),
                    AppFunctionModel(
                        title = "Project - 01 (Custom Analog Watch)",
                        onClick = {
                            goTo(JAnalogWatchActivity::class.java)
                        }
                    )
                )
            )
        }
    }
}

@Composable
fun AppFunctions(
    functions: List<AppFunctionModel>,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(
            items = functions,
        ) {
            Box(Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable(onClick = it.onClick)
            ) {
                Text(it.title, modifier = Modifier.align(Alignment.Center))
            }
            Divider()
        }
    }
}

data class AppFunctionModel(
    val title: String,
    val onClick: () -> Unit,
)
