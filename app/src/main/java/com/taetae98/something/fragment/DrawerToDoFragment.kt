package com.taetae98.something.fragment

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.taetae98.something.R
import com.taetae98.something.adapter.ToDoAdapter
import com.taetae98.something.base.BindingFragment
import com.taetae98.something.databinding.FragmentDrawerTodoBinding
import com.taetae98.something.dto.ToDo
import com.taetae98.something.repository.SettingRepository
import com.taetae98.something.repository.ToDoRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class DrawerToDoFragment : BindingFragment<FragmentDrawerTodoBinding>(R.layout.fragment_drawer_todo) {
    @Inject
    lateinit var todoRepository: ToDoRepository

    @Inject
    lateinit var settingRepository: SettingRepository

    private val args by navArgs<DrawerToDoFragmentArgs>()
    private val drawer by lazy { args.drawer }
    private var finishedToDoVisible by Delegates.observable(false) { _, _, _ ->
        submitList()
    }
    private var todoList by Delegates.observable(emptyList<ToDo>()) { _, _, _ ->
        submitList()
    }

    private fun submitList() {
        todoAdapter.submitList(if (finishedToDoVisible) {
            todoList
        } else {
            todoList.filter { !it.isFinished }
        })
    }

    private val todoAdapter by lazy {
        ToDoAdapter().apply {
            onToDoClickCallback = {
                findNavController().navigate(DrawerToDoFragmentDirections.actionDrawerToDoFragmentToTodoEditActivity(it))
            }
            onToDoLongClickCallback = {
                findNavController().navigate(DrawerToDoFragmentDirections.actionDrawerToDoFragmentToTodoBottomMenuDialog(it))
            }
        }
    }

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
        onCreateRecyclerView()
        onCreateOnToDoAdd()

        return binding.root
    }

    override fun onCreateViewDataBinding() {
        super.onCreateViewDataBinding()
        binding.drawer = drawer
    }

    private fun onCreateSupportActionBar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun onCreateRecyclerView() {
        binding.recyclerView.adapter = todoAdapter
    }

    private fun onCreateOnToDoAdd() {
        binding.setOnToDoAdd {
            findNavController().navigate(DrawerToDoFragmentDirections.actionDrawerToDoFragmentToTodoEditActivity(
                ToDo(drawerId = drawer.id)
            ))
        }
    }

    private fun onCreateToDoList() {
        if (settingRepository.showFinishedToDoInDrawerFragment.value!!) {
            todoRepository.findByDrawerIdLiveData(drawer.id).observe(viewLifecycleOwner) {
                todoList = it
            }
        } else {
            todoRepository.findByDrawerIdAndNotFinishedLiveData(drawer.id).observe(viewLifecycleOwner) {
                todoList = it
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_drawer_todo_fragment, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.show_finished_todo).isChecked = finishedToDoVisible
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit -> {
                findNavController().navigate(DrawerToDoFragmentDirections.actionDrawerToDoFragmentToDrawerEditActivity(drawer))
            }
            R.id.show_finished_todo -> {
                finishedToDoVisible = !finishedToDoVisible
            }
        }

        return super.onOptionsItemSelected(item)
    }
}