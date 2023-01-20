package com.apjake.compose_canvas.domain.usecase

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.apjake.compose_canvas.common.ext.withZeroLeading
import com.apjake.compose_canvas.domain.model.DummyItem
import kotlin.random.Random

/**
 * Created by AP-Jake
 * on Jan 20, 2023
 */

private val characterList = "abcdefghijklmnopqrstuvwxyz".toList()

class GetOrderListUseCase {

    private val colorList = GetRandomColorsUseCase()

    private fun getRandomWord(
        min: Int = 1,
        max: Int = 6,
    ): String {
        return characterList
            .shuffled()
            .subList(0, Random.nextInt(min, max))
            .joinToString("")
            .capitalize(Locale.current)
    }

    private fun getRandomDate(
        year: String? = null,
        month: String? = null,
    ): String {
        val day = Random.nextInt(1, 28).withZeroLeading(2)
        val rMonth = month ?: Random.nextInt(1, 12).withZeroLeading(2)
        val rYear = year ?: Random.nextInt(2020, 2022).toString()
        return "$day - $rMonth - $rYear"
    }

    operator fun invoke(size: Int) = (1..size).map {
        DummyItem(
            id = "ID - $it",
            code = "Order - ${it.withZeroLeading(3)}",
            name = (1..Random.nextInt(1, 5)).joinToString(" ") { getRandomWord() },

            color = colorList[Random.nextInt(0, colorList.size - 1)]
        )
    }

}
