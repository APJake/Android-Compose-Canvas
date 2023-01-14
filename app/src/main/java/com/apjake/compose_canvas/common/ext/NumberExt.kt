package com.apjake.compose_canvas.common.ext

import kotlin.math.PI

/**
 * Created by AP-Jake
 * on Jan 14, 2023
 */

val Int.asRadian: Float
    get() = this.toFloat().asRadian

val Float.asRadian: Float
    get() = this * (PI / 180f).toFloat()

val Int.asDegree: Float
    get() = this.toFloat().asDegree

val Float.asDegree: Float
    get() = this * (180f / PI).toFloat()

fun Int.withZeroLeading(
    size: Int,
): String {
    var tmp = this.toString()
    while (tmp.length < size) {
        tmp = "0$tmp"
    }
    return tmp
}
