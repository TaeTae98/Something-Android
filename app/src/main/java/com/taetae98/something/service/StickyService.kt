package com.taetae98.something.service

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import com.taetae98.something.manager.NotificationManager
import com.taetae98.something.receiver.StickyReceiver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StickyService : Service() {
    @Inject
    lateinit var notificationManager: NotificationManager

    private val stickyReceiver = StickyReceiver()

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NotificationManager.STICKY_NOTIFICATION_ID, notificationManager.createStickyNotification())
        registerReceiver(stickyReceiver, IntentFilter(Intent.ACTION_SCREEN_OFF))
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(stickyReceiver)
    }
}