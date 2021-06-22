package com.taetae98.something.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.card.MaterialCardView
import com.taetae98.something.R
import com.taetae98.something.base.BaseAdapter
import com.taetae98.something.base.BaseHolder
import com.taetae98.something.databinding.HolderCalendarBinding
import com.taetae98.something.dto.Date
import kotlin.properties.Delegates

class ToDoCalendarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : MaterialCardView(context, attrs, defStyleAttr) {
    companion object {
        private val itemCallback = object : DiffUtil.ItemCallback<Date>() {
            override fun areItemsTheSame(oldItem: Date, newItem: Date): Boolean {
                return oldItem.timeInMillis == newItem.timeInMillis
            }

            override fun areContentsTheSame(oldItem: Date, newItem: Date): Boolean {
                return oldItem == newItem
            }
        }
    }

    private val viewPager by lazy { ViewPager2(context) }
    private val viewPagerAdapter by lazy { CalendarViewAdapter() }

    private var current by Delegates.observable(Date()) { _, _, _ ->

    }
    private var currentPosition = Int.MAX_VALUE / 2

    init {
        with(viewPager) {
            adapter = viewPagerAdapter
            setCurrentItem(currentPosition, false)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (currentPosition != position) {
                        current.add(Date.MONTH, position - currentPosition)
                        currentPosition = position
                    }
                }
            })
        }

        addView(viewPager)
    }

    inner class CalendarViewAdapter : BaseAdapter<Date>(itemCallback) {
        init {
            setHasStableIds(true)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, Date> {
            return CalendarHolder(
                HolderCalendarBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

        override fun getItemId(position: Int): Long {
            return getItem(position).timeInMillis
        }

        override fun getItemViewType(position: Int): Int {
            return R.layout.holder_calendar
        }

        override fun getItemCount(): Int {
            return Int.MAX_VALUE
        }

        override fun getItem(position: Int): Date {
            return current.copy(Date.MONTH, position - currentPosition)
        }

        inner class CalendarHolder(binding: HolderCalendarBinding) : BaseHolder<HolderCalendarBinding, Date>(binding) {
            override fun onBind(element: Date) {
                super.onBind(element)
                binding.date = element
            }
        }
    }
}