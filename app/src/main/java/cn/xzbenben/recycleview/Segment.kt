package cn.xzbenben.recycleview

import android.content.Context
import android.view.View

/**
 * view可重复使用的片段
 * Created by xinjun on 2020/7/6 15:45
 */
open class Segment<T>(var ctx: Context) {
    var layoutId: Int = 0
    var viewInstance: View? = null//记录列表视图中的header或footer,在item中无效

    //绑定
    var bindCb: (BindingContext<T>.() -> Unit)? = null

    fun bind(bind: BindingContext<T>.() -> Unit) {
        this.bindCb = bind
    }

    fun layout(v: Int) {
        layoutId = v
    }
}

fun <T> segment(ctx: Context, func: Segment<T>.() -> Unit): Segment<T> {
    val segment = Segment<T>(ctx)
    segment.func()
    return segment
}

/**
 * 数据视图绑定上下文
 * Created by xinjun on 2020/7/6 15:48
 */
class BindingContext<T>(v: View, var data: T?) {

    private val finder = Finder(v)

    fun <T : View> find(id: Int): T = finder.find(id)

    fun <T : View> find(id: Int, function: T.() -> Unit) = finder.find(id, function)

}