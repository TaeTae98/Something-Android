package com.taetae98.something.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.taetae98.something.manager.NotificationManager
import com.taetae98.something.repository.SettingRepository
import com.taetae98.something.repository.ToDoRepository
import com.taetae98.something.service.StickyService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RebootReceiver : BroadcastReceiver() {
    @Inject
    lateinit var settingRepository: SettingRepository

    @Inject
    lateinit var todoRepository: ToDoRepository

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            initStickyService(context)
            initToDoNotification()
        }
    }

    private fun initStickyService(context: Context) {
        if (settingRepository.isSticky.value == true) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(Intent(context, StickyService::class.java))
            } else {
                context.startService(Intent(context, StickyService::class.java))
            }
        }
    }

    private fun initToDoNotification() {
        todoRepository.findIsNotFinishedAndIsNotificationLiveData().value?.let {
            notificationManager.notify(it)
        }
    }
}