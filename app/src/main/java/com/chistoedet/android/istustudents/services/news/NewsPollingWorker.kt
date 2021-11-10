package com.chistoedet.android.istustudents.services.news

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.chistoedet.android.istustudents.Config
import com.chistoedet.android.istustudents.di.SharedRepositoryImpl
import com.chistoedet.android.istustudents.notification.NotificationProvider
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.dto.common.id.UserId
import com.vk.sdk.api.wall.WallService
import com.vk.sdk.api.wall.dto.WallGetResponse
import com.vk.sdk.api.wall.dto.WallWallpostFull


class NewsPollingWorker(var context: Context, workerParameters: WorkerParameters)
    : Worker(context, workerParameters) {

   // private var component = DaggerActivityComponent.factory().create(context)


    var sharedRepository : SharedRepositoryImpl = SharedRepositoryImpl(context)

    init {
       // component.inject(this)
    }

    override fun doWork(): Result {

        if (

        VK.isLoggedIn()) {

            VK.execute(
                WallService().wallGet(
                    UserId(Config.VK_PUBLIC_ID),
                    null,
                    null,
                    5,
                    null,
                    null), object: VKApiCallback<WallGetResponse> {
                    override fun success(result: WallGetResponse) {

                       if (sharedRepository.getNewsHistorySize() == 0) {
                           sharedRepository.setNewsHistorySize(result.count)
                        }

                        if (result.count > sharedRepository.getNewsHistorySize()) {
                            val delta = result.count - sharedRepository.getNewsHistorySize()
                            for (i in 1..delta) {
                                val post = result.items[i]
                                    NotificationProvider.showNotificationPost(applicationContext, post)
                                }
                            }
                        // TODO for tests
                        else {
                            NotificationProvider.showNotificationPost(applicationContext, WallWallpostFull())
                        }

                        sharedRepository.setNewsHistorySize(result.count)
                    }
                    override fun fail(error: Exception) {

                    }
                }
            )}


        return Result.success()
    }

}
