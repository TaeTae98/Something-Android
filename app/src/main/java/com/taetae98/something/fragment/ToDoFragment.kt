package com.taetae98.something.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.taetae98.something.R
import com.taetae98.something.adapter.ToDoAdapter
import com.taetae98.something.base.BindingFragment
import com.taetae98.something.databinding.FragmentTodoBinding
import com.taetae98.something.repository.DrawerRepository
import com.taetae98.something.repository.ToDoRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ToDoFragment : BindingFragment<FragmentTodoBinding>(R.layout.fragment_todo) {
    @Inject
    lateinit var todoRepository: ToDoRepository

    @Inject
    lateinit var drawerRepository: DrawerRepository

    init {
        setHasOptionsMenu(true)
    }

    private val todoAdapter by lazy {
        ToDoAdapter().apply {
            onToDoClickCallback = {
                findNavController().navigate(ToDoFragmentDirections.actionTodoFragmentToToDoEditActivity(it))
            }
            onToDoLongClickCallback = {
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

    private fun onCreateToDoList() {
        todoRepository.findInToDoFragmentLiveData().observe(viewLifecycleOwner) {
            todoAdapter.submitList(it)
        }
    }

    private fun onCreateOnToDoAdd() {
        binding.setOnToDoAdd {
            findNavController().navigate(ToDoFragmentDirections.actionTodoFragmentToToDoEditActivity())
        }
    }
}