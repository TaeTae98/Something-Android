package com.taetae98.something.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.taetae98.something.R
import com.taetae98.something.base.BaseAdapter
import com.taetae98.something.base.BaseHolder
import com.taetae98.something.databinding.HolderDrawerBinding
import com.taetae98.something.dto.Drawer

class DrawerAdapter : BaseAdapter<Drawer>(itemCallback) {
    companion object {
        private val itemCallback = object : DiffUtil.ItemCallback<Drawer>() {
            override fun areItemsTheSame(oldItem: Drawer, newItem: Drawer): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Drawer, newItem: Drawer): Boolean {
                return oldItem == newItem
            }
        }
    }

    var onDrawerClickCallback: ((Drawer) -> Unit)? = null
    var onDrawerLongClickCallback: ((Drawer) -> Unit)? = null

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, Drawer> {
        return DrawerHolder(
            HolderDrawerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.holder_drawer
    }

    inner class DrawerHolder(binding: HolderDrawerBinding) : BaseHolder<HolderDrawerBinding, Drawer>(binding) {
        init {
            itemView.setOnClickListener {
                onDrawerClickCallback?.invoke(element)
            }

            itemView.setOnLongClickListener {
                onDrawerLongClickCallback?.invoke(element)
                true
            }
        }
        override fun onBind(element: Drawer) {
            super.onBind(element)
            binding.drawer = element
        }
    }
}