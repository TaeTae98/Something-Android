package com.taetae98.something.base

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseHolder<VB: ViewDataBinding, E: Any>(protected val binding: VB) : RecyclerView.ViewHolder(binding.root) {
    val context: Context
        get() { return itemView.context }

    lateinit var element: E

    open fun onBind(element: E) {
        this.element = element
    }

    open fun onBind(element: E, payload: MutableList<Any>) {

    }
}