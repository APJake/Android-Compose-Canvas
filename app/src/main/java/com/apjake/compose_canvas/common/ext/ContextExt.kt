package com.apjake.compose_canvas.common.ext

import android.app.Activity
import android.content.Intent

fun Activity.goTo(
    activity: Class<out Activity>,
    removeStack: Boolean = false,
) {
    startActivity(Intent(this, activity))
    if (removeStack) {
        finish()
    }
}
