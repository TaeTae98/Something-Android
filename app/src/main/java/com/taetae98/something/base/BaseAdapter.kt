package com.taetae98.something.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseAdapter<E: Any>(itemCallback: DiffUtil.ItemCallback<E>) : ListAdapter<E, BaseHolder<out ViewDataBinding, E>>(itemCallback) {
    override fun onBindViewHolder(holder: BaseHolder<out ViewDataBinding, E>, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onBindViewHolder(holder: BaseHolder<out ViewDataBinding, E>, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        holder.onBind(getItem(position), payloads)
    }
}