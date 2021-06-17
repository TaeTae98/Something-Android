package com.taetae98.something.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.activityViewModels
import com.taetae98.something.R
import com.taetae98.something.base.BindingFragment
import com.taetae98.something.databinding.FragmentTodoEditBinding
import com.taetae98.something.dto.Date
import com.taetae98.something.dto.ToDo
import com.taetae98.something.repository.DrawerRepository
import com.taetae98.something.repository.ToDoRepository
import com.taetae98.something.viewmodel.ToDoEditViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ToDoEditFragment : BindingFragment<FragmentTodoEditBinding>(R.layout.fragment_todo_edit) {
    @Inject
    lateinit var todoRepository: ToDoRepository

    @Inject
    lateinit var drawerRepository: DrawerRepository

    private val viewModel by activityViewModels<ToDoEditViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateSupportActionBar()
        onCreateOnSetBeginDate()
        onCreateOnSetEndDate()
        onCreateDrawer()
        onCreateOnFinish()

        return binding.root
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.viewModel = viewModel
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onCreateOnSetBeginDate() {
        binding.setOnSetBeginDate {
            DatePickerDialog(
                    requireContext(),
                    { _, year, month, dayOfMonth ->
                        viewModel.beginDate.value = Date(year, month, dayOfMonth)
                        if (viewModel.endDate.value == null || viewModel.beginDate.value!! > viewModel.endDate.value!!) {
                            viewModel.endDate.value = Date(year, month, dayOfMonth)
                        }
                    },
                    viewModel.beginDate.value?.year ?: Calendar.getInstance().get(Calendar.YEAR),
                    viewModel.beginDate.value?.month ?: Calendar.getInstance().get(Calendar.MONTH),
                    viewModel.beginDate.value?.dayOfMonth ?: Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun onCreateOnSetEndDate() {
        binding.setOnSetEndDate {
            DatePickerDialog(
                    requireContext(),
                    { _, year, month, dayOfMonth ->
                        viewModel.endDate.value = Date(year, month, dayOfMonth)
                        if (viewModel.beginDate.value == null || viewModel.beginDate.value!! > viewModel.endDate.value!!) {
                            viewModel.beginDate.value = Date(year, month, dayOfMonth)
                        }
                    },
                    viewModel.endDate.value?.year ?: Calendar.getInstance().get(Calendar.YEAR),
                    viewModel.endDate.value?.month ?: Calendar.getInstance().get(Calendar.MONTH),
                    viewModel.endDate.value?.dayOfMonth ?: Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun onCreateDrawer() {
        CoroutineScope(Dispatchers.IO).launch {
            with(binding.drawerTextInputLayout.editText as AutoCompleteTextView) {
                val array = drawerRepository.findAll()

                withContext(Dispatchers.Main) {
                    setText(array.find { it.id == viewModel.drawerId.value }?.name, false)
                    setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, array.map { it.name }))
                    setOnItemClickListener { _, _, position, _ ->
                        viewModel.drawerId.value = array[position].id
                    }
                }
            }
        }
    }

    private fun onCreateOnFinish() {
        binding.setOnFinish {
            onFinish()
        }
    }

    private fun onFinish() {
        if (viewModel.title.value!!.isBlank()) {
            binding.titleTextInputLayout.error = getString(R.string.enter_the_title)
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            todoRepository.insert(ToDo.Factory.createFromToDoEditViewModel(viewModel))
        }

        finish()
    }
}