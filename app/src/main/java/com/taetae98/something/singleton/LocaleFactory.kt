package com.taetae98.something.singleton

import java.util.*

object LocaleFactory {
    fun getLocale(): Locale {
        val locale = Locale.getDefault()
        return if (locale == Locale.KOREA || locale == Locale.KOREAN) {
            Locale.getDefault()
        } else {
            Locale.ENGLISH
        }
    }
}