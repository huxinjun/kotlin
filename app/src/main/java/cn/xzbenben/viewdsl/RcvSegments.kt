package cn.xzbenben.viewdsl

import android.content.Context
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * type info,include pos and data
 * Created by xinjun on 2020/7/7 10:21
 */
class Info(p: Int, d: Any?) {
    var pos: Int = p
        private set
        get
    var data: Any? = d
        private set
        get
}

class SegmentSets(var ctx: Context) {


    private val headerInitIndex = -1//must litter than 0
    private val headerCapacity = 100
    private val footerInitIndex = headerInitIndex - headerCapacity
    private val footerCapacity = 100
    private var headerTypeIndex = headerInitIndex
    private var footerTypeIndex = footerInitIndex


    var data: MutableList<Any> = mutableListOf()
    var typeBlock: (Info.() -> Int)? = null
    var mSegments = mutableMapOf<Int, Segment<*>>()


    fun type(block: Info.() -> Int) {
        this.typeBlock = block
    }


    fun <T> header2(func: () -> Segment<T>) {
        if (headerTypeIndex <= footerInitIndex)
            throw RuntimeException("header max support count $headerCapacity")
        mSegments.put(headerTypeIndex--, func())
    }

    fun <T> footer2(func: () -> Segment<T>) {
        if (footerTypeIndex <= footerInitIndex - footerCapacity)
            throw RuntimeException("footer max support count $footerCapacity")
        @Suppress("UNCHECKED_CAST")
        mSegments.put(footerTypeIndex--, func())
    }

    fun <T> item2(type: Int, func: () -> Segment<T>) {
        checkViewType(type)
        mSegments.put(type, func())
    }

    fun <T> item2(func: () -> Segment<T>) {
        item2(0, func)
    }

    fun checkViewType(viewType: Int) {
        if (viewType <= headerInitIndex)
            throw RuntimeException(
                "item view type must be equal or greatter than $headerInitIndex" +
                        ",because header and footer was used view type begin $headerInitIndex to ${footerInitIndex - footerCapacity + 1}"
            )
    }

    fun headerSize(): Int {
        var count = 0
        mSegments.keys.forEach {
            if (it <= headerInitIndex && it > footerInitIndex)
                count++
        }
        return count
    }

    fun footerSize(): Int {
        var count = 0
        mSegments.keys.forEach {
            if (it <= footerInitIndex && it > footerInitIndex - footerCapacity)
                count++
        }
        return count
    }

    fun isHeader(viewType: Float): Boolean =
        viewType.toInt() in (footerInitIndex + 1)..headerInitIndex

    fun isHeader(position: Int): Boolean = position < headerSize()

    fun isFooter(viewType: Float): Boolean =
        viewType.toInt() <= footerInitIndex && viewType.toInt() > footerInitIndex - footerCapacity

    fun isFooter(position: Int): Boolean = position >= headerSize() + data.size

    fun headerPos2Type(pos: Int): Int = headerInitIndex - pos

    fun headerType2Pos(viewType: Int): Int = viewType.abs() - headerInitIndex.abs()

    fun footerPos2Type(pos: Int): Int = footerInitIndex - (pos - headerSize() - data.size)

    fun footerType2Pos(viewType: Int): Int =
        viewType.abs() - footerInitIndex.abs() + (headerSize() + data.size)

    fun headerIndex2ViewType(i: Int) = headerInitIndex - i

    fun footerIndex2ViewType(i: Int) = footerInitIndex - i
}


inline fun RecyclerView.templete(crossinline init: SegmentSets.() -> Unit) {
    this.adapter = RecyclerViewAdpt<Any> {
        val set = SegmentSets(context)
        set.init()
        set
    }
}

inline fun RecyclerView.data(init: () -> List<Any>) {
    data(false, init)
}

inline fun RecyclerView.data(append: Boolean, init: () -> List<Any>) {
    if (adapter == null)
        throw RuntimeException("data invoke must after templete{...}")
    @Suppress("UNCHECKED_CAST")
    val adpt = adapter as RecyclerViewAdpt<*>
    with(adpt) {
        if (!append) segmentSets.data.clear()
        segmentSets.data.addAll(init())
        notifyDataSetChanged()
    }

}


//**************************************
/**
 * get a header view by position from RecyclerView
 */
fun RecyclerView.header(i: Int): View? {
    if (adapter == null)
        throw RuntimeException("header invoke must after with templete{...}")
    val segmentSets = (adapter as RecyclerViewAdpt<*>).segmentSets
    val targetViewType = segmentSets.headerIndex2ViewType(i)
    segmentSets.mSegments.forEach { (viewType, segment) ->
        segmentSets.run {
            Log.i("xinjun", "header.targetViewType=$targetViewType,curr=$viewType")
            if (targetViewType == viewType)
                return segment.viewInstance
        }
    }
    return null
}

/**
 * get a footer view by position from RecyclerView
 */
fun RecyclerView.footer(i: Int): View? {
    if (adapter == null)
        throw RuntimeException("footer invoke must after with templete{...}")
    val segmentSets = (adapter as RecyclerViewAdpt<*>).segmentSets
    val targetViewType = segmentSets.footerIndex2ViewType(i)
    segmentSets.mSegments.forEach { (viewType, segment) ->
        segmentSets.run {
            if (targetViewType == viewType)
                return segment.viewInstance
        }
    }
    return null
}