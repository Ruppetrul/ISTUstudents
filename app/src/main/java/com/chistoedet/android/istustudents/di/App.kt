package com.chistoedet.android.istustudents.di

import android.app.Application
import android.content.SharedPreferences
import androidx.work.*
import com.chistoedet.android.core.ISTUService
import com.chistoedet.android.istustudents.network.response.user.UserResponse
import com.chistoedet.android.istustudents.services.news.NewsPollingWorker
import java.util.concurrent.TimeUnit

const val NOTIFICATION_CHANNEL_ID = "flickr_poll"

class App : Application() {

    private val SHAREDNAME = "ISTU_SHARED_PREFERENCES"
    val NOTIFY_WORK = "NOTIFY_WORK"

    private lateinit var component: AppComponent
    private lateinit var sharedPreferences: SharedPreferences

    private var user : UserResponse ?= null

    override fun onCreate() {
        super.onCreate()

        // Test notify

        // for first start
        startNewsPolling()

    }

    fun startNewsPolling() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicRequest =
            PeriodicWorkRequest.Builder(NewsPollingWorker::class.java, 15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            NOTIFY_WORK,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicRequest)


    }

    fun getLoginService() : ISTUService {
        return component.getApiService()
    }

    // VK

    fun getUser() : UserResponse? {
        return user
    }

    fun setUser(user : UserResponse) {
        this.user = user
    }



    /* suspend fun apiGetUser() : UserResponse {
         return component.getApiService().testUser(login.getTokenType() + login.getAccessToken())
     }

     fun getActivityComponent*/

}