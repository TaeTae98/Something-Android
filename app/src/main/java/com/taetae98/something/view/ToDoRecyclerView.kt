package com.taetae98.something.view

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.setPadding
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.taetae98.something.R
import com.taetae98.something.adapter.ToDoAdapter
import com.taetae98.something.repository.ToDoRepository
import com.taetae98.something.toDp
import com.taetae98.something.utility.SpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class ToDoRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : RecyclerView(context, attrs, defStyleAttr) {
    @Inject
    lateinit var todoRepository: ToDoRepository

    init {
        clipToPadding = false
        context.theme.obtainStyledAttributes(attrs, R.styleable.SettingItemView, defStyleAttr, defStyleRes).apply {
            val margin = getInt(R.styleable.ToDoRecyclerView_item_margin, 10)
            setPadding((margin / 2).toDp())
            addItemDecoration(SpacingItemDecoration((margin / 2).toDp()))
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                when (viewHolder) {
                    is ToDoAdapter.ToDoHolder -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            if (viewHolder.element.isFinished) {
                                todoRepository.delete(viewHolder.element)
                                withContext(Dispatchers.Main) {
                                    Snackbar.make(this@ToDoRecyclerView, R.string.delete, Snackbar.LENGTH_LONG)
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
                                    Snackbar.make(this@ToDoRecyclerView, R.string.finish, Snackbar.LENGTH_LONG)
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
        }).attachToRecyclerView(this)
    }
}