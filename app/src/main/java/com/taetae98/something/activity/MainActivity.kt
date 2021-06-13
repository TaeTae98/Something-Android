package com.taetae98.something.activity

import android.os.Bundle
import androidx.navigation.ui.setupWithNavController
import com.taetae98.something.R
import com.taetae98.something.base.BindingActivity
import com.taetae98.something.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateBottomNavigationView()
    }

    private fun onCreateBottomNavigationView() {
        with(binding.bottomNavigationView) {
            setupWithNavController(navController)
        }
    }
}