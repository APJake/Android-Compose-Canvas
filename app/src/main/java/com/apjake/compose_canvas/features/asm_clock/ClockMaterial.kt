package com.apjake.compose_canvas.features.asm_clock

/**
 * Created by AP-Jake
 * on Jan 14, 2023
 */
data class ClockMaterial(
    val hour: Int = 0,
    val minute: Int = 0,
    val second: Int = 0,
) {
    fun tick(): ClockMaterial {
        var min = minute
        var hr = hour
        var sec = second + 1

        if (sec >= 60) {
            min++
            sec = 0
        }
        if (min >= 60) {
            hr++
            min = 0
        }
        if (hr >= 12) {
            hr = 0
        }

        return ClockMaterial(
            second = sec,
            minute = min,
            hour = hr
        )
    }
}
