package com.apjake.compose_canvas.features.weight_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apjake.compose_canvas.common.base.ComposeActivity

class WeightPickerActivity : ComposeActivity() {
    override fun renderView(): @Composable () -> Unit = {
        var weight by remember {
            mutableStateOf(60)
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Select your weight", fontSize = 30.sp)
            Spacer(Modifier.height(120.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(weight.toString(), fontSize = 60.sp)
                Text("KG", fontSize = 22.sp)
            }
            Spacer(Modifier.height(60.dp))
            Scale(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp),
                minWeight = 20,
                maxWeight = 200,
                scaleStyle = ScaleStyle(
                    scaleWidth = 160.dp,
                    scaleIndicatorColor = Color.Magenta,
                    tenStepLineColor = Color.Black,
                    speed = 1.5f
                ),
                initialWeight = 60
            ) {
                weight = it
            }
        }
    }
}

