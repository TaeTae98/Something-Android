package com.taetae98.something.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.taetae98.something.R
import com.taetae98.something.adapter.ToDoAdapter
import com.taetae98.something.base.BindingDialog
import com.taetae98.something.databinding.DialogCalendarDayBinding
import com.taetae98.something.dto.Term
import com.taetae98.something.dto.ToDo
import com.taetae98.something.repository.ToDoRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CalendarDayDialog : BindingDialog<DialogCalendarDayBinding>(R.layout.dialog_calendar_day) {
    @Inject
    lateinit var todoRepository: ToDoRepository

    private val args by navArgs<CalendarDayDialogArgs>()
    private val date by lazy { args.date }
    private val showFinishedToDo by lazy { args.showFinishedToDo }

    private val todoAdapter by lazy { ToDoAdapter().apply {
        onToDoClickCallback = {
            findNavController().navigate(CalendarDayDialogDirections.actionCalendarDayDialogToTodoEditActivity(it))
        }
        onToDoLongClickCallback = {
            findNavController().navigate(CalendarDayDialogDirections.actionCalendarDayDialogToTodoBottomMenuDialog(it))
        }
    } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        setAnimation(R.style.Theme_Something_Animation_Popup)
        onCreateToDoList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateRecyclerView()
        onCreateOnToDoAdd()

        return binding.root
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.date = date
    }

    private fun onCreateRecyclerView() {
        binding.todoRecyclerView.adapter = todoAdapter
    }

    private fun onCreateOnToDoAdd() {
        binding.setOnToDoAdd {
            findNavController().navigate(CalendarDayDialogDirections.actionCalendarDayDialogToTodoEditActivity(
                ToDo(term = Term(beginDate = date, endDate = date))
            ))
        }
    }

    private fun onCreateToDoList() {
        if (showFinishedToDo) {
            todoRepository.findInCalendarDayDialogLiveData(date).observe(viewLifecycleOwner) {
                todoAdapter.submitList(it)
            }
        } else {
            todoRepository.findByNotFinishedInCalendarDayDialogLiveData(date).observe(viewLifecycleOwner) {
                todoAdapter.submitList(it)
            }
        }
    }
}