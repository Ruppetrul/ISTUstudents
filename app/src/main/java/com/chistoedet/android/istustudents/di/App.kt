package com.chistoedet.android.istustudents.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.chistoedet.android.istustudents.ISTUService
import com.chistoedet.android.istustudents.UserInformation
import com.chistoedet.android.istustudents.network.response.login.LoginResponse
import com.chistoedet.android.istustudents.network.response.user.UserResponse


class App : Application() {

    private val SHAREDNAME = "ISTU_SHARED_PREFERENCES"

    private lateinit var component: AppComponent

    private lateinit var sharedPreferences: SharedPreferences

    private var login : LoginResponse ?= null
    private var user : UserResponse ?= null

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.factory().create()
        sharedPreferences = this.getSharedPreferences(SHAREDNAME,Context.MODE_PRIVATE)
    }

    fun getLoginService() : ISTUService{
        return component.getApiService()
    }

    fun getToken() : String {

        val token = sharedPreferences.getString("token", "")
        val tokenType = sharedPreferences.getString("token-type", "")

        return "$tokenType $token"
    }

    fun saveLastLogin(email: String?) {
        sharedPreferences.edit().putString("lastLogin", email).apply()
    }

    fun setLogin(login: LoginResponse) {
        this.login = login

        saveToken(login)
        sharedPreferences.apply {
            edit().putString("token", login.getAccessToken()).apply()
            edit().putString("token-type", login.getTokenType()).apply()
        }
    }

    private fun saveToken(login: LoginResponse) {
        sharedPreferences.edit()
            .putString("token",login.getAccessToken())
            .putString("token-type",login.getTokenType())
            .apply()
    }

    fun getUser() : UserResponse? {
        return if (user!!.getId() != null) {
            user
        } else null
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
        login = null
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