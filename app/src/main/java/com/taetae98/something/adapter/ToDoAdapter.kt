package com.taetae98.something.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.taetae98.something.R
import com.taetae98.something.base.BaseAdapter
import com.taetae98.something.base.BaseHolder
import com.taetae98.something.databinding.HolderTodoBinding
import com.taetae98.something.dto.ToDo

class ToDoAdapter : BaseAdapter<ToDo>(itemCallback) {
    companion object {
        val itemCallback = object : DiffUtil.ItemCallback<ToDo>() {
            override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
                return oldItem == newItem
            }
        }
    }

    var onToDoClickCallback: ((ToDo) -> Unit)? = null

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, ToDo> {
        return ToDoHolder(
            HolderTodoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.holder_todo
    }

    inner class ToDoHolder(binding: HolderTodoBinding) : BaseHolder<HolderTodoBinding, ToDo>(binding) {
        init {
            itemView.setOnClickListener {
                onToDoClickCallback?.invoke(element)
            }
        }

        override fun bind(element: ToDo) {
            super.bind(element)
            binding.todo = element
        }
    }
}