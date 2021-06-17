package com.taetae98.something.fragment

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.taetae98.something.R
import com.taetae98.something.base.BindingFragment
import com.taetae98.something.databinding.FragmentCalendarBinding

class CalendarFragment : BindingFragment<FragmentCalendarBinding>(R.layout.fragment_calendar) {
    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_calendar_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.drawer -> {

            }
        }

        return super.onOptionsItemSelected(item)
    }
}