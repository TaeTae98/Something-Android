package com.taetae98.something.fragment

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import com.taetae98.something.R
import com.taetae98.something.base.BindingFragment
import com.taetae98.something.databinding.FragmentCalendarBinding
import com.taetae98.something.dto.Date
import com.taetae98.something.repository.ToDoRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CalendarFragment : BindingFragment<FragmentCalendarBinding>(R.layout.fragment_calendar) {
    @Inject
    lateinit var todoRepository: ToDoRepository

    init {
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreateToDoList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateSupportActionBar()
        onCreateCalendarView()

        return binding.root
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onCreateToDoList() {
        todoRepository.findInCalendarFragmentLiveData().observe(viewLifecycleOwner) {
            binding.calendarView.setToDo(it)
        }
    }

    private fun onCreateCalendarView() {
        binding.calendarView.onDateClickListener = {
            findNavController().navigate(CalendarFragmentDirections.actionCalendarFragmentToCalendarDayDialog(it, true))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_calendar_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.today -> {
                binding.calendarView.setDate(Date())
            }
        }

        return super.onOptionsItemSelected(item)
    }
}