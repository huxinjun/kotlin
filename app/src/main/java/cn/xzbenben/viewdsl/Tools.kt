package cn.xzbenben.viewdsl

import android.view.View


inline fun <T : View> T?.safe(function: T.() -> Unit) {
    if (this == null)
        return
    this.function()
}


fun Int.abs(): Int = kotlin.math.abs(this)