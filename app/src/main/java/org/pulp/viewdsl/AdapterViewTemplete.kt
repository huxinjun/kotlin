package org.pulp.viewdsl

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter


class ItemViewAdapter<T>(var set: LvSegmentSets) : BaseAdapter() {

    constructor(init: () -> LvSegmentSets) : this(init())

    @Suppress("UNCHECKED_CAST")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: VH<T>


        var view = convertView

        if (view == null || view.tag == null) {
            view =
                LayoutInflater.from(set.ctx).inflate(set.mSegment?.layoutId ?: 0, parent, false)
            holder = VH(view!!)
            view.tag = holder
        } else {
            Log.i("xinjun", "use holder $position")
            holder = view.tag as VH<T>
        }

        val segment = set.mSegment as Segment<T>

        segment.bindCb?.let {
            val itemData = set.data[position]
            BindingContext(holder.item, itemData as T?).it()
        }

        return view

    }

    override fun getItem(position: Int): Any? {
        return set.data[position]
    }

    override fun getItemId(position: Int): Long = position.toLong()
    override fun getCount(): Int = set.data.size
}