package com.taetae98.something.view

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.taetae98.something.R
import com.taetae98.something.toDp
import com.taetae98.something.utility.SpacingItemDecoration

class DrawerRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : RecyclerView(context, attrs, defStyleAttr) {
    init {
        clipToPadding = false
        context.theme.obtainStyledAttributes(attrs, R.styleable.SettingItemView, defStyleAttr, defStyleRes).apply {
            val margin = getInt(R.styleable.DrawerRecyclerView_item_margin, 10)
            setPadding((margin / 2).toDp())
            addItemDecoration(SpacingItemDecoration((margin / 2).toDp()))
        }
    }
}