package com.taetae98.something.view

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.*
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.taetae98.something.R
import com.taetae98.something.base.BaseAdapter
import com.taetae98.something.base.BaseHolder
import com.taetae98.something.databinding.HolderCalendarBinding
import com.taetae98.something.dto.Date
import com.taetae98.something.dto.ToDo
import java.util.*
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
    private var currentToDoList by Delegates.observable(emptyList<ToDo>()) { _, _, _ ->
        viewPagerAdapter.notifyDataSetChanged()
    }
    private var currentPosition = Int.MAX_VALUE / 2

    var onDateClickListener: ((Date) -> Unit)? = null

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

    fun setDate(year: Int, month: Int, dayOfMonth: Int) {
        setDate(Date(year, month, dayOfMonth))
    }

    fun setDate(date: Date) {
        if (current != date) {
            if (current.year != date.year || current.month != date.month) {
                if (current < date) {
                    current = date
                    currentPosition++
                    viewPager.setCurrentItem(currentPosition, true)
                } else {
                    current = date
                    currentPosition--
                    viewPager.setCurrentItem(currentPosition, true)
                }
            } else {
                current = date
            }
        }
    }

    fun setToDo(list: List<ToDo>) {
        currentToDoList = list
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
            private val firstDay: Date
                get() {
                    return element.copy().apply {
                        dayOfMonth = 1
                        add(Date.DAY_OF_MONTH, 1 - dayOfWeek)
                    }
                }
            private val rows: List<GridLayout>
                get() {
                    return arrayListOf<View>().apply {
                        itemView.findViewsWithText(this, "row", View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION)
                    }.map {
                        it as GridLayout
                    }
                }

            init {
                binding.setOnCalendarHeadClick {
                    DatePickerDialog(context, { _, year, month, dayOfMonth ->
                        setDate(year, month, dayOfMonth)
                    }, current.year, current.month, current.dayOfMonth).show()
                }

                rows.forEachIndexed { y, view ->
                    val detector = GestureDetector(context, object : GestureDetector.OnGestureListener {
                        override fun onDown(e: MotionEvent?): Boolean {
                            return true
                        }

                        override fun onShowPress(e: MotionEvent?) {

                        }

                        override fun onSingleTapUp(e: MotionEvent): Boolean {
                            val x = (e.x / (view.width / 7)).toInt()
                            val date = firstDay.copy(Date.DAY_OF_MONTH, (y * 7 + x))
                            setDate(date)
                            onDateClickListener?.invoke(date)
                            return true
                        }

                        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
                            return true
                        }

                        override fun onLongPress(e: MotionEvent?) {

                        }

                        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
                            return true
                        }
                    })

                    view.setOnTouchListener { _, event ->
                        view.performClick()
                        detector.onTouchEvent(event)
                    }
                }
            }

            override fun onBind(element: Date) {
                super.onBind(element)
                binding.date = element

                onBindDay()
                onBindToDo()
            }

            private fun onBindDay() {
                val day = firstDay
                val today = Date()
                for (gridLayout in rows) {
                    for (i in 0 until 7) {
                        with((gridLayout[i] as LinearLayout)[0] as TextView) {
                            if (day == today) {
                                setTodayDayView(this, day)
                            } else {
                                setDayView(this, day)
                            }
                        }

                        day.add(Date.DAY_OF_MONTH, 1)
                    }
                }
            }

            private fun setTodayDayView(view: TextView, date: Date) {
                with(view) {
                    setBackgroundResource(R.drawable.ic_circle_24)
                    val outValue = TypedValue()
                    context.theme.resolveAttribute(R.attr.colorOnPrimary, outValue, true)

                    text = date.dayOfMonth.toString()
                    alpha = if (date.month == element.month) 1.0F else 0.3F
                    setTextColor(context.getColor(outValue.resourceId))
                }
            }

            private fun setDayView(view: TextView, date: Date) {
                with(view) {
                    background = null
                    text = date.dayOfMonth.toString()
                    alpha = if (date.month == element.month) 1.0F else 0.3F
                    val outValue = TypedValue()
                    setTextColor(context.getColor(when(date.dayOfWeek) {
                        Calendar.SATURDAY -> {
                            context.theme.resolveAttribute(R.attr.colorSaturday, outValue, true)
                            outValue.resourceId
                        }
                        Calendar.SUNDAY -> {
                            context.theme.resolveAttribute(R.attr.colorSunday, outValue, true)
                            outValue.resourceId
                        }
                        else -> {
                            context.theme.resolveAttribute(R.attr.colorWeek, outValue, true)
                            outValue.resourceId
                        }
                    }))
                }
            }

            private fun onBindToDo() {
                val begin = firstDay
                for (gridLayout in rows) {
                    val end = begin.copy(Date.DAY_OF_MONTH, 6)
                    val list = currentToDoList.filter {
                        (it.term!!.beginDate!! <= begin && begin <= it.term!!.endDate!!) || (begin <= it.term!!.beginDate!! && it.term!!.endDate!! <= end) || (it.term!!.beginDate!! <= end && end <= it.term!!.endDate!!)
                    }.toMutableList()

                    with(gridLayout) {
                        removeViews(7, childCount - 7)
                        var pos: Int
                        var beginPos: Int
                        var endPos: Int
                        while (list.isNotEmpty()) {
                            pos = 0
                            val iterator = list.iterator()
                            while (iterator.hasNext()) {
                                val todo = iterator.next()
                                if (todo.term!!.beginDate!! <= begin && pos == 0) {
                                    beginPos = 0
                                    endPos = if (todo.term!!.endDate!! <= end) todo.term!!.endDate!!.dayOfWeek - 1 else 6
                                    pos = endPos + 1
                                    addView(ToDoView(context).apply { onBind(todo, beginPos, endPos) })
                                    iterator.remove()
                                } else if (todo.term!!.beginDate!! >= begin && pos <= todo.term!!.beginDate!!.dayOfWeek - 1) {
                                    beginPos = todo.term!!.beginDate!!.dayOfWeek - 1
                                    endPos = if (todo.term!!.endDate!! <= end) todo.term!!.endDate!!.dayOfWeek - 1 else 6

                                    addView(ToDoView(context).apply { onBind(todo, beginPos, endPos) })
                                    pos = endPos + 1
                                    iterator.remove()
                                }
                            }
                        }
                    }

                    begin.dayOfMonth += 7
                }
            }

            inner class ToDoView : MaterialTextView {
                constructor(context: Context) : super(context)
                constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
                constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

                private lateinit var todo: ToDo

                init {
                    gravity = Gravity.CENTER
                    setTextColor(context.getColor(R.color.white))
                    setTypeface(null, Typeface.BOLD)

                    isSingleLine = true
                    ellipsize = TextUtils.TruncateAt.MARQUEE
                    isSelected = true
                }

                fun onBind(todo: ToDo, begin: Int, end: Int) {
                    this.todo = todo
                    text = todo.title

                    val colorId = resources.getIdentifier("item${todo.id % 16}", "color", context.packageName)
                    setBackgroundColor(context.getColor(colorId))

                    layoutParams = GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1), GridLayout.spec(begin, end - begin + 1, 1F)).apply {
                        width = 0
                    }
                }
            }
        }
    }
}