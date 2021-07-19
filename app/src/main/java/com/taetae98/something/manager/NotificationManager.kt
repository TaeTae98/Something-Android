package com.taetae98.something.manager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.taetae98.something.R
import com.taetae98.something.dto.ToDo
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationManager @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    private val manager by lazy { NotificationManagerCompat.from(context) }

    companion object {
        const val STICKY_NOTIFICATION_ID = 1
        const val BACKUP_IN_PROGRESS_NOTIFICATION_ID = 2
        const val BACKUP_FINISH_NOTIFICATION_ID = 3
        const val RESTORE_IN_PROGRESS_NOTIFICATION_ID = 4
        const val RESTORE_FINISH_NOTIFICATION_ID = 5

        private const val STICKY_CHANNEL_ID = "com.taetae98.something.sticky.channel"
        private const val STICKY_CHANNEL_NAME = "Something Sticky"

        private const val BACKUP_CHANNEL_ID = "com.taetae98.something.backup"
        private const val BACKUP_CHANNEL_NAME = "Something Backup"

        private const val TODO_CHANNEL_ID = "com.taetae98.something.todo"
        private const val TODO_CHANNEL_NAME = "Something ToDo"

        private const val TODO_GROUP_KEY = "com.taetae98.ToDo.Notification.ToDo.GROUP_KEY"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(id: String, name: String, importance: Int) {
        NotificationChannel(id, name, importance).also {
            manager.createNotificationChannel(it)
        }
    }

    fun createStickyNotification(): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(STICKY_CHANNEL_ID, STICKY_CHANNEL_NAME, NotificationManager.IMPORTANCE_MIN)
        }

        val contentIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.navigation_main)
            .setDestination(R.id.todoFragment)
            .createPendingIntent()

        return NotificationCompat.Builder(context, STICKY_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_todo_24)
            .setShowWhen(false)
            .setContentTitle(context.getString(R.string.run_on_unlock))
            .setContentIntent(contentIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setVisibility(NotificationCompat.VISIBILITY_SECRET)
            .build()
    }

    fun createBackupNotification(isInProgress: Boolean = true): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(BACKUP_CHANNEL_ID, BACKUP_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        }

        return NotificationCompat.Builder(context, BACKUP_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_backup_24)
            .setContentTitle(context.getString(R.string.backup))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(isInProgress)
            .setProgress(1, 1, isInProgress)
            .build()
    }

    fun createRestoreNotification(isInProgress: Boolean = true): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(BACKUP_CHANNEL_ID, BACKUP_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        }

        return NotificationCompat.Builder(context, BACKUP_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_restore_24)
            .setContentTitle(context.getString(R.string.restore))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(isInProgress)
            .setProgress(1, 1, isInProgress)
            .build()
    }


    fun notify(list: List<ToDo>) {
        manager.cancelAll()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(TODO_CHANNEL_ID, TODO_CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)
        }

        list.forEach {
            val notification = NotificationCompat.Builder(context, TODO_CHANNEL_ID).apply {
                setSmallIcon(R.drawable.ic_todo_24)
                setContentTitle(it.title)
                setContentText(it.description)
                setSubText(it.term?.toString() ?: "")
                setShowWhen(false)
                setOngoing(true)
                setGroup(TODO_GROUP_KEY)
                setStyle(
                    NotificationCompat.BigTextStyle()
                )
            }.build()

            manager.notify(it.id.toInt() + 100, notification)
        }

        if (list.size >= 2) {
            val summary = NotificationCompat.Builder(context, TODO_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_todo_24)
                .setShowWhen(false)
                .setOngoing(true)
                .setGroup(TODO_GROUP_KEY)
                .setGroupSummary(true)
                .build()

            manager.notify(0, summary)
        }
    }

    fun notify(id: Int, notification: Notification) {
        manager.notify(id, notification)
    }
}