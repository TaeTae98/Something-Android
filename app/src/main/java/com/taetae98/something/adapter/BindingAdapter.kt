package com.taetae98.something.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.taetae98.something.dto.Date
import com.taetae98.something.singleton.LocaleFactory
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("calendarHead")
    fun setCalendarHead(view: TextView, date: Date?) {
        if (date == null) {
            view.text = ""
            return
        }

        val locale = LocaleFactory.getLocale()
        if (locale == Locale.ENGLISH) {
            val format = SimpleDateFormat("MMM yyyy", Locale.ENGLISH)
            view.text = format.format(date.timeInMillis)
        } else {
            val format = SimpleDateFormat("yyyy. MM.", Locale.KOREAN)
            view.text = format.format(date.timeInMillis)
        }
    }
}