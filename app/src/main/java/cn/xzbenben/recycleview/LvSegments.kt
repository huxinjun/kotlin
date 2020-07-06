package cn.xzbenben.recycleview

import android.content.Context
import android.widget.ListView


class LvSegmentSets(var ctx: Context) {


    var data: List<*> = emptyList<Any>()
    var mSegment: Segment<*>? = null

    fun <T> item(func: () -> Segment<T>) {
        mSegment = func()
    }
}


fun <T> ItemViewAdapter<T>.data(data: List<T>) {
    set.data = data
    notifyDataSetChanged()
}

inline fun ListView.templete(init: LvSegmentSets.() -> Unit) {
    val set = LvSegmentSets(context)
    set.init()
    this.adapter = ItemViewAdapter<Any>(set)

}


inline fun <T> ListView.data(init: () -> List<T>) {
    if (adapter == null)
        throw RuntimeException("data invoke must after templete{...}")
    @Suppress("UNCHECKED_CAST")
    (adapter as ItemViewAdapter<T>).data(init())
}