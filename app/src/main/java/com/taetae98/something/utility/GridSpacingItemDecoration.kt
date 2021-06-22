package com.taetae98.something.utility

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(private val spanCount: Int, private val dp: Int, private val orientation: Int = RecyclerView.VERTICAL, private val includeEdge: Boolean = true) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildLayoutPosition(view)
        when(orientation) {
            RecyclerView.VERTICAL -> {
                val column = position % spanCount
                if (includeEdge) {
                    outRect.left = dp - column * dp / spanCount
                    outRect.right = (column + 1) * dp / spanCount
                    outRect.bottom = dp
                    if (position < spanCount) {
                        outRect.top = dp
                    }
                } else {
                    outRect.left = column * dp / spanCount
                    outRect.right = dp - (column + 1) * dp / spanCount
                    if (position >= spanCount) {
                        outRect.top = dp
                    }
                }
            }
            RecyclerView.HORIZONTAL -> {
                val row = position % spanCount
                if (includeEdge) {
                    outRect.top = dp - row * dp / spanCount
                    outRect.bottom = (row + 1) * dp / spanCount
                    outRect.right = dp
                    if (position < spanCount) {
                        outRect.left = dp
                    }
                } else {
                    outRect.top = row * dp / spanCount
                    outRect.bottom = dp - (row + 1) * dp / spanCount
                    if (position >= spanCount) {
                        outRect.left = dp
                    }
                }
            }
        }
    }
}