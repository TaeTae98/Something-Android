package com.taetae98.something.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.taetae98.something.dto.Date

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("date")
    fun setDate(view: TextView, date: Date?) {
        view.text = (date ?: Date()).toString()
    }

    @JvmStatic
    @BindingAdapter("error")
    fun setError(view: TextInputLayout, error: String?) {
        view.error = error
    }
}