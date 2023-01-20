package com.apjake.compose_canvas.domain.usecase

import androidx.compose.ui.graphics.Color

/**
 * Created by AP-Jake
 * on Jan 20, 2023
 */

object GetRandomColorsUseCase {

    private val colorList = listOf(
        Color(0xFFEECCCC),
        Color(0xFFFFECD6),
        Color(0xFFFFFBCF),
        Color(0xFFF0FFD5),
        Color(0xFFE1FFDA),
        Color(0xFFDAFFE2),
        Color(0xFFD8FFF2),
        Color(0xFFDCFCFF),
        Color(0xFFD9EBFF),
        Color(0xFFD7D8FF),
        Color(0xFFE5DAFF),
        Color(0xFFF4DAFF),
        Color(0xFFFFDAF4),
    )

    operator fun invoke() = colorList.shuffled()

}