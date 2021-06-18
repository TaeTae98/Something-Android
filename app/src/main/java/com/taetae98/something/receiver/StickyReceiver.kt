package com.taetae98.something.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.navigation.NavDeepLinkBuilder
import com.taetae98.something.R

class StickyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        NavDeepLinkBuilder(context)
            .setGraph(R.navigation.navigation_main)
            .setDestination(R.id.todoFragment)
            .createPendingIntent().send()
    }
}