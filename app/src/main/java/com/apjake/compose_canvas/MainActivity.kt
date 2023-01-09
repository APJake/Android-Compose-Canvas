package com.apjake.compose_canvas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apjake.compose_canvas.common.base.ComposeActivity
import com.apjake.compose_canvas.common.ext.goTo
import com.apjake.compose_canvas.features.star_clicker.StarClickerActivity

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
    LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        items(
            items = functions,
        ) {
            Button(onClick = it.onClick) {
                Text(
                    text = it.title,
                )
            }
            Spacer(Modifier.height(20.dp))
        }
    }
}

data class AppFunctionModel(
    val title: String,
    val onClick: () -> Unit,
)
