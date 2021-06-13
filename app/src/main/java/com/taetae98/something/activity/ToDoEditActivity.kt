package com.taetae98.something.activity

import com.taetae98.something.R
import com.taetae98.something.base.BindingActivity
import com.taetae98.something.databinding.ActivityToDoEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToDoEditActivity : BindingActivity<ActivityToDoEditBinding>(R.layout.activity_to_do_edit)