package com.chistoedet.android.istustudents.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.chistoedet.android.core.ISTUService
import com.chistoedet.android.istustudents.UserInformation
import com.chistoedet.android.istustudents.network.response.login.LoginResponse
import com.chistoedet.android.istustudents.network.response.user.UserResponse


class App : Application() {

    private val SHAREDNAME = "ISTU_SHARED_PREFERENCES"

    private lateinit var component: AppComponent
    private lateinit var sharedPreferences: SharedPreferences

    private var user : UserResponse ?= null

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.factory().create()
        sharedPreferences = this.getSharedPreferences(SHAREDNAME,Context.MODE_PRIVATE)
    }

    fun getLoginService() : ISTUService {
        return component.getApiService()
    }

    fun getToken() : String? {

        val token = sharedPreferences.getString("token", null)
        val tokenType = sharedPreferences.getString("token-type", null)

        return if (token == null || tokenType == null) {
            null
        } else "$tokenType $token"

    }

    fun saveLastLogin(email: String?) {
        sharedPreferences.edit().putString("lastLogin", email).apply()
    }

    fun saveToken(login: LoginResponse) {
            sharedPreferences.apply {
            edit().putString("token", login.getAccessToken()).apply()
            edit().putString("token-type", login.getTokenType()).apply()
        }
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