package com.apjake.compose_canvas.features.projects.card_slider

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.apjake.compose_canvas.common.base.ComposeActivity
import com.apjake.compose_canvas.domain.usecase.GetOrderListUseCase
import com.apjake.compose_canvas.domain.usecase.GetRandomColorsUseCase
import com.google.accompanist.pager.ExperimentalPagerApi

/**
 * Created by AP-Jake
 * on Jan 20, 2023
 */

@ExperimentalFoundationApi
@ExperimentalPagerApi
class JCardSliderActivity : ComposeActivity() {


    override fun renderView(): @Composable () -> Unit = {
        Surface(Modifier.fillMaxSize(), color = Color.LightGray) {
            JCardSlider(
                colors = GetRandomColorsUseCase(),
                onPageChanged = {
                    Log.d("JCardSlider", "slided to page $it")
                },
                getPageText = {
                    "Page - $it"
                }
            )
        }
    }
}
