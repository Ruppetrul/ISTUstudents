package com.chistoedet.android.istustudents.di

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.chistoedet.android.core.ISTUService
import com.chistoedet.android.istustudents.R
import com.chistoedet.android.istustudents.UserInformation
import com.chistoedet.android.istustudents.network.response.user.UserResponse

const val NOTIFICATION_CHANNEL_ID = "flickr_poll"

class App : Application() {

    private val SHAREDNAME = "ISTU_SHARED_PREFERENCES"

    private lateinit var component: AppComponent
    private lateinit var sharedPreferences: SharedPreferences

    private var user : UserResponse ?= null

    override fun onCreate() {
        super.onCreate()

        // Test notify
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
            val notificationManager: NotificationManager =
                getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
        //
        component = DaggerAppComponent.factory().create()
        sharedPreferences = this.getSharedPreferences(SHAREDNAME,Context.MODE_PRIVATE)
    }

    fun getLoginService() : ISTUService {
        return component.getApiService()
    }

    // VK

    fun saveVkAccessToken(token: String) {

    }


    fun saveLastLogin(email: String?) {
        sharedPreferences.edit().putString("lastLogin", email).apply()
    }

    fun getUser() : UserResponse? {
        return user
    }

    fun setUser(user : UserResponse) {
        this.user = user
    }

    fun getUserInformation() : UserInformation{
        val passport = sharedPreferences.getString("${user?.getId()}_passport", null)
        val inn = sharedPreferences.getString("${user?.getId()}_inn", null)
        val snils = sharedPreferences.getString("${user?.getId()}_snils", null)

        val userInfo = UserInformation()
        userInfo.passport = passport
        userInfo.inn = inn
        userInfo.snils = snils

        return userInfo
    }

    fun logout() {
        user = null
        sharedPreferences.edit().remove("token").apply()
        sharedPreferences.edit().remove("token-type").apply()
    }

    fun saveUserInfo(saveInformation: UserInformation) {
        sharedPreferences.edit().putString("${user?.getId()}_passport",saveInformation.passport).apply()
        sharedPreferences.edit().putString("${user?.getId()}_inn", saveInformation.inn).apply()
        sharedPreferences.edit().putString("${user?.getId()}_snils", saveInformation.snils).apply()
    }


    fun getLastLogin() : String? {
        return sharedPreferences.getString("lastLogin", "")
    }


    /* suspend fun apiGetUser() : UserResponse {
         return component.getApiService().testUser(login.getTokenType() + login.getAccessToken())
     }

     fun getActivityComponent*/

}