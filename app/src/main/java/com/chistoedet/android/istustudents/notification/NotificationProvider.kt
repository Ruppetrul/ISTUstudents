package com.chistoedet.android.istustudents.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.chistoedet.android.istustudents.Config
import com.chistoedet.android.istustudents.R
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.dto.common.id.UserId
import com.vk.sdk.api.wall.WallService
import com.vk.sdk.api.wall.dto.WallGetResponse
import com.vk.sdk.api.wall.dto.WallWallpostFull

class NotificationProvider {

    companion object {
        val NOTIFICATION_CHANNEL_ID = "test_channel"
        val REQUEST_CODE = "REQUEST_CODE"

        fun showNotificationPost(context: Context, wallPostResponse: WallWallpostFull?) : Boolean {
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

            wallPostResponse.apply {
                if (this != null) {
                    val notification = NotificationCompat
                        .Builder(context, NOTIFICATION_CHANNEL_ID)
                        .setTicker("Ticker")
                        .setSmallIcon(android.R.drawable.ic_menu_report_image)
                        .setContentTitle("Новость")
                        .setContentText(wallPostResponse?.postSource?.data?.substring(0,100))
                        //.setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build()
                    notificationManager.notify(0, notification)
                } else {

                    VK.execute(
                        WallService().wallGet(
                            UserId(Config.VK_PUBLIC_ID),
                            null,
                            null,
                            1,
                            null,
                            null), object: VKApiCallback<WallGetResponse> {
                            override fun fail(error: Exception) {

                            }

                            override fun success(result: WallGetResponse) {
                                val notification = NotificationCompat
                                    .Builder(context, NOTIFICATION_CHANNEL_ID)
                                    .setTicker("Ticker")
                                    .setSmallIcon(android.R.drawable.ic_menu_report_image)
                                    .setContentTitle("Сделаем вид что вышла новость...")
                                    .setContentText(result.items.first().text)
                                    //.setContentIntent(pendingIntent)
                                    .setAutoCancel(true)
                                    .build()
                                notificationManager.notify(0, notification)
                            }
                        })


                }
            }

            //val requestCode = intent.getIntExtra(REQUEST_CODE, 0)

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