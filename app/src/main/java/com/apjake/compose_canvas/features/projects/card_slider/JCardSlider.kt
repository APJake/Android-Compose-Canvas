package com.apjake.compose_canvas.features.projects.card_slider

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlin.math.absoluteValue

/**
 * Created by AP-Jake
 * on Jan 20, 2023
 */

@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun JCardSlider(
    colors: List<Color>,
    onPageChanged: (Int) -> Unit,
    getPageText: (Int) -> String,
) {
    val totalSize = remember {
        Int.MAX_VALUE
    }
    val initialPage = totalSize / 2
    val pageState = rememberPagerState(initialPage)
    LaunchedEffect(pageState.currentPage) {
        onPageChanged(pageState.currentPage)
    }

    Column {
        HorizontalPager(
            count = totalSize,
            state = pageState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                horizontal = 40.dp,
                vertical = 20.dp
            )
        ) { page ->
            val colorIndex = page % colors.size
            JItemCard(
                text = getPageText(page - initialPage),
                color = colors[colorIndex],
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                        // We animate the scaleX + scaleY, between 85% and 100%
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    })
        }
        JSliderIndicator()
    }
}

@Composable
fun JItemCard(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        elevation = 0.dp,
        backgroundColor = color,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(
                horizontal = 30.dp,
                vertical = 35.dp
            )
        ) {
            Text(text)
        }
    }
}

@Composable
fun JSliderIndicator(
    modifier: Modifier = Modifier,
) {
    var sliderState by remember {
        mutableStateOf(10)
    }
    Box(modifier = modifier
        .fillMaxWidth()
        .height(100.dp)) {
        JSlider(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.TopCenter)
            ,
            minValue = 0,
            maxValue = 100,
            sliderStyle = JSliderStyle(
                elevation = 0.dp,
                scaleWidth = 100.dp,
                scaleIndicatorColor = Color.Magenta,
                speed = 1.5f
            ),
            initialValue = sliderState
        ) {
            Log.d("JSlider", "slide changed to: $it")
//            sliderState = it
        }

    }
}
