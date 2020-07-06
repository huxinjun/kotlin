package cn.xzbenben.recycleview

import android.view.View


inline fun <T : View> T?.safe(function: T.() -> Unit) {
    if (this == null)
        return
    this.function()
}


fun Int.abs(): Int {
    return kotlin.math.abs(this)
}