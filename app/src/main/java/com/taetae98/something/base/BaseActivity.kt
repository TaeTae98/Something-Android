package com.taetae98.something.base

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.taetae98.something.R

abstract class BaseActivity : AppCompatActivity() {
    protected val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment).navController
    }

    protected open val appBarConfiguration by lazy { AppBarConfiguration(setOf()) }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        super.setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}