package com.taetae98.something.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.taetae98.something.R
import com.taetae98.something.adapter.ToDoAdapter
import com.taetae98.something.base.BindingFragment
import com.taetae98.something.databinding.FragmentTodoBinding
import com.taetae98.something.repository.ToDoRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class ToDoFragment : BindingFragment<FragmentTodoBinding>(R.layout.fragment_todo) {
    @Inject
    lateinit var todoRepository: ToDoRepository

    private val todoAdapter by lazy {
        ToDoAdapter().apply {
            onToDoClickCallback = {
                findNavController().navigate(ToDoFragmentDirections.actionTodoFragmentToToDoBottomMenuDialog(it))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreateToDoList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        onCreateSupportActionBar()
        onCreateRecyclerView()
        onCreateItemTouchHelper()
        onCreateOnToDoAdd()

        return binding.root
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onCreateRecyclerView() {
        with(binding.recyclerView) {
            adapter = todoAdapter
        }
    }

    private fun onCreateItemTouchHelper() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (viewHolder) {
                    is ToDoAdapter.ToDoHolder -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            if (viewHolder.element.isFinished) {
                                todoRepository.delete(viewHolder.element)
                                withContext(Dispatchers.Main) {
                                    Snackbar.make(binding.recyclerView, R.string.delete, Snackbar.LENGTH_LONG)
                                            .setActionTextColor(resources.getColor(R.color.yellow_green, null))
                                            .setAction(R.string.cancel) {
                                                CoroutineScope(Dispatchers.IO).launch {
                                                    todoRepository.insert(viewHolder.element)
                                                }
                                            }.show()
                                }
                            } else {
                                todoRepository.update(viewHolder.element.copy(isFinished = true))
                                withContext(Dispatchers.Main) {
                                    Snackbar.make(binding.recyclerView, R.string.finish, Snackbar.LENGTH_LONG)
                                            .setActionTextColor(resources.getColor(R.color.yellow_green, null))
                                            .setAction(R.string.cancel) {
                                                CoroutineScope(Dispatchers.IO).launch {
                                                    todoRepository.insert(viewHolder.element)
                                                }
                                            }.show()
                                }
                            }
                        }
                    }
                }
            }
        }).attachToRecyclerView(binding.recyclerView)
    }

    private fun onCreateToDoList() {
        todoRepository.findAllLiveDate().observe(viewLifecycleOwner) {
            todoAdapter.submitList(it)
        }
    }

    private fun onCreateOnToDoAdd() {
        binding.setOnToDoAdd {
            findNavController().navigate(ToDoFragmentDirections.actionTodoFragmentToToDoEditActivity())
        }
    }
}