package com.example.ethan.fnppokedex.Common

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.support.annotation.DimenRes
import android.support.v7.widget.RecyclerView

class ItemOffsetDecoration(private val itemOffset:Int):RecyclerView.ItemDecoration()  {
    constructor(context: Context, @DimenRes itemDimensId:Int): this(context.resources.getDimensionPixelSize(itemDimensId))


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect!!.set(itemOffset,itemOffset,itemOffset,itemOffset)
    }

}