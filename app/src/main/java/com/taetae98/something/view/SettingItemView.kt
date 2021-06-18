package com.taetae98.something.view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setPadding
import com.taetae98.something.R
import com.taetae98.something.databinding.ViewSettingItemBinding
import com.taetae98.something.toDp
import com.taetae98.something.utility.DataBinding

class SettingItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0): ConstraintLayout(context, attrs, defStyleAttr, defStyleRes), DataBinding<ViewSettingItemBinding> {
    override val binding: ViewSettingItemBinding by lazy {
        DataBinding.get(
            this,
            R.layout.view_setting_item
        )
    }

    init {
        setPadding(15.toDp())
        context.theme.obtainStyledAttributes(attrs, R.styleable.SettingItemView, defStyleAttr, defStyleRes).apply {
            if (isInEditMode) {
                inflate(context, R.layout.view_setting_item, this@SettingItemView)
                findViewById<TextView>(R.id.title_text_view).apply {
                    text = getString(R.styleable.SettingItemView_title)
                }
            } else {
                with(binding) {
                    title = getString(R.styleable.SettingItemView_title)
                }
            }
        }
    }
}