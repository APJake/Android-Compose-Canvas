package com.apjake.compose_canvas.features.weight_picker

/**
 * Created by AP-Jake
 * on Jan 14, 2023
 */
sealed class LineType {
    object Normal : LineType()
    object FiveStep : LineType()
    object TenStep : LineType()
}
