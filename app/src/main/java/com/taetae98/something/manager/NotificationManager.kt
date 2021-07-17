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
        private const val STICKY_CHANNEL_ID = "Something Sticky"
        private const val STICKY_CHANNEL_NAME = "Something Sticky"

        private const val TODO_CHANNEL_ID = "Something ToDo"
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

            manager.notify(it.id.toInt(), notification)
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
}