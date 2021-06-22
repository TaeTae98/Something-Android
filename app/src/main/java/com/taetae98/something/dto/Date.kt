package com.taetae98.something.dto

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

data class Date(
    var year: Int = Calendar.getInstance().get(Calendar.YEAR),
    var month: Int = Calendar.getInstance().get(Calendar.MONTH),
    var dayOfMonth: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
) : Serializable, Comparable<Date> {
    companion object {
        const val DAY_OF_MONTH = 1 shl 0
        const val MONTH = 1 shl 1
        const val YEAR = 1 shl 2
        private val MAX_DAY_OF_MONTH = arrayOf(
            arrayOf(
                31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
            ),
            arrayOf(
                31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
            )
        )

        fun isLeapYear(year: Int): Boolean {
            return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))
        }
    }

    val dayOfWeek: Int
        get() {
            return GregorianCalendar(year, month, dayOfMonth).get(Calendar.DAY_OF_WEEK)
        }

    val maxDayOfMonth: Int
        get() {
            return MAX_DAY_OF_MONTH[if (isLeapYear(year)) 1 else 0][month]
        }

    val timeInMillis: Long
        get() {
            return GregorianCalendar(year, month, dayOfMonth).timeInMillis
        }

    fun copy(flag: Int, diff: Int): Date {
        return copy().apply {
            add(flag, diff)
        }
    }

    fun add(flag: Int, diff: Int) {
        when(flag) {
            YEAR -> {
                year += diff
            }
            MONTH -> {
                month += diff
                refreshMonth()
                dayOfMonth = dayOfMonth.coerceIn(1, maxDayOfMonth)
            }
            DAY_OF_MONTH -> {
                dayOfMonth += diff
                refreshDayOfMonth()
            }
        }
    }

    private fun refreshMonth() {
        while (month >= 12) {
            month -= 12
            year += 1
        }
        while (month < 0) {
            month += 12
            year -= 1
        }
    }

    private fun refreshDayOfMonth() {
        while (dayOfMonth > maxDayOfMonth) {
            dayOfMonth -= maxDayOfMonth
            month += 1
            refreshMonth()
        }
        while (dayOfMonth <= 0) {
            month -= 1
            refreshMonth()
            dayOfMonth += maxDayOfMonth
        }
    }

    override fun compareTo(other: Date): Int {
        return compareValues(timeInMillis, other.timeInMillis)
    }

    override fun toString(): String {
        return SimpleDateFormat.getDateInstance().format(timeInMillis)
    }

    class Factory {
        companion object {
            fun createFromTimeInMillis(timeInMillis: Long): Date {
                val calendar = GregorianCalendar().apply {
                    this.timeInMillis = timeInMillis
                }

                return Date(
                    calendar[Calendar.YEAR],
                    calendar[Calendar.MONTH],
                    calendar[Calendar.DAY_OF_MONTH]
                )
            }
        }
    }
}