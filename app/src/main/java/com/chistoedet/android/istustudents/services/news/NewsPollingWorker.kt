package com.chistoedet.android.istustudents.services.news

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.chistoedet.android.istustudents.Config
import com.chistoedet.android.istustudents.notification.NotificationProvider
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.dto.common.id.UserId
import com.vk.sdk.api.wall.WallService
import com.vk.sdk.api.wall.dto.WallGetResponse


class NewsPollingWorker(var context: Context, workerParameters: WorkerParameters)
    : Worker(context, workerParameters) {

    override fun doWork(): Result {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            context.mainExecutor.execute {
                Toast.makeText(applicationContext,"doWork", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(applicationContext, "doWork", Toast.LENGTH_LONG).show()
        }


        if (
           // token != null &&
            VK.isLoggedIn()) {

            VK.execute(
                WallService().wallGet(
                    UserId(Config.VK_PUBLIC_ID),
                    null,
                    null,
                    null,
                    null,
                    null), object: VKApiCallback<WallGetResponse> {
                    override fun success(result: WallGetResponse) {

                        result.items.last().text?.let { NotificationProvider.showNotification(applicationContext, it.substring(0,100)) }

                    }
                    override fun fail(error: Exception) {

                    }
                }
            )}


        return Result.success()
    }


        //TODO перенести в провайдер
        fun getToken() : String? {
            val SHAREDNAME = "ISTU_SHARED_PREFERENCES"
            var sharedPreferences = context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE)
            val token = sharedPreferences.getString("token", null)
            val tokenType = sharedPreferences.getString("token-type", null)

            return if (token == null || tokenType == null) {
                null
            } else "$tokenType $token"

        }

        }
