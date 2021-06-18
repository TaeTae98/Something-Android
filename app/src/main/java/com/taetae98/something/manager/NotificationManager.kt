package com.taetae98.something.manager

import android.app.Notification
import android.app.NotificationChannel
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.taetae98.something.R
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
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(id: String, name: String, importance: Int) {
        NotificationChannel(id, name, importance).also {
            manager.createNotificationChannel(it)
        }
    }

    fun createStickyNotification(): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(STICKY_CHANNEL_ID, STICKY_CHANNEL_NAME, NotificationManagerCompat.IMPORTANCE_MIN)
        }

        val contentIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.navigation_main)
            .setDestination(R.id.todoFragment)
            .createPendingIntent()

        return NotificationCompat.Builder(context, STICKY_CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_todo_24)
            setShowWhen(false)
            setContentTitle(context.getString(R.string.run_on_unlock))
            setContentIntent(contentIntent)
        }.build()
    }
}