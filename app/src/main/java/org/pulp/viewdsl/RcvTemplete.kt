package org.pulp.viewdsl

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView ViewHolder
 * Created by xinjun on 2020/7/6 18:50
 */
class VH<T>(v: View) : RecyclerView.ViewHolder(v) {

    var item: View = v
    var mFinder = finder(item) {}
    var itemSegment: Segment<T>? = null

    fun <T : View> get(id: Int): T {
        return mFinder.find(id)
    }
}

/**
 * a common adapter for RecyclerView
 * Created by xinjun on 2020/7/6 16:00
 */
class RecyclerViewAdpt<T>(var segmentSets: SegmentSets) : RecyclerView.Adapter<VH<T>>() {

    constructor(init: () -> SegmentSets) : this(init())

    override fun getItemViewType(position: Int): Int {
        segmentSets.run {
            //Log.i("xinjun", "pos=$position,isHeader=${isHeader(position)},isFooter=${isFooter(position)},headersize=${headerSize()},datasize=${data.size}")
            if (isHeader(position))
                return headerPos2Type(position)
            else if (isFooter(position))
                return footerPos2Type(position)
        }
        val realPos = position - segmentSets.headerSize()
//        Log.i("xinjun", "pos=$position,realpos=$realPos,headersize=${set.headerSize()}")
        val data = Info(realPos, segmentSets.data.get(realPos))
        if (segmentSets.typeBlock == null)
            return 0
        val viewType = segmentSets.typeBlock!!(data)
        segmentSets.checkViewType(viewType)
        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH<T> {
        Log.i("xinjun", "onCreateViewHolder.viewType=$viewType")
        val segment = segmentSets.mSegments[viewType]
        var view: View? = null
        segment?.run {
            view = LayoutInflater.from(segmentSets.ctx)
                .inflate(segment.layoutId, parent, false)


        }

        val a = segmentSets.isHeader(viewType.toFloat())
        val b = segmentSets.isFooter(viewType.toFloat())

        Log.i(
            "xinjun",
            "onCreateViewHolder.viewType=$viewType,viewInstance=$view,segment=$segment,isHeader=$a,isFooter=$b"
        )
        segmentSets.run {
            if (isHeader(viewType.toFloat()) || isFooter(viewType.toFloat())) {
                segment?.viewInstance = view
            }
        }


        val vh = VH<T>(view!!)
        @Suppress("UNCHECKED_CAST")
        vh.itemSegment = segment as Segment<T>?
        return vh
    }

    override fun getItemCount() =
        segmentSets.data.size + segmentSets.headerSize() + segmentSets.footerSize()

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: VH<T>, position: Int) {
        holder.itemSegment?.bindCb?.let {
            if (segmentSets.isHeader(position) || segmentSets.isFooter(position))
                BindingContext(holder.item, null as T).it()
            else {
                val itemData = segmentSets.data.get(position - segmentSets.headerSize())
                BindingContext(holder.item, itemData as T?).it()
            }
        }
    }
}
