package com.chistoedet.android.istustudents.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.chistoedet.android.istustudents.R
import com.vk.sdk.api.wall.dto.WallWallpostFull

class NotificationProvider {

    companion object {
        val NOTIFICATION_CHANNEL_ID = "test_channel"
        val REQUEST_CODE = "REQUEST_CODE"

        fun showNotificationPost(context: Context, wallPostResponse: WallWallpostFull) : Boolean {
            val notificationManager = NotificationManagerCompat.from(context)
            if (!notificationManager.areNotificationsEnabled()) return false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = getNotificationChannel(NOTIFICATION_CHANNEL_ID, context)
                notificationManager.createNotificationChannel(channel)

                if (channel.importance == NotificationManager.IMPORTANCE_NONE) return false

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    val channelGroup = notificationManager.getNotificationChannelGroup(channel.group)
                    if (channelGroup != null && channelGroup.isBlocked) return false

                }
            }

            //val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            val notification = NotificationCompat
                .Builder(context, NOTIFICATION_CHANNEL_ID)
                .setTicker("Ticker")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("Новость")
                .setContentText(wallPostResponse.text?.substring(0,100))
                //.setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            //val requestCode = intent.getIntExtra(REQUEST_CODE, 0)

            notificationManager.notify(0, notification)
            return true

        }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun getNotificationChannel(channelId: String, context: Context) : NotificationChannel {
            val name = context.getString(R.string.notification_channel_name)
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(channelId, name, importance)
            //channel.group = "Test group"
            return channel
        }
    }

}