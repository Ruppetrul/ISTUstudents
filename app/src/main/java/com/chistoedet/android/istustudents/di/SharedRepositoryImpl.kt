package com.chistoedet.android.istustudents.di

import android.content.Context
import android.content.SharedPreferences
import com.chistoedet.android.istustudents.network.response.login.LoginResponse
import javax.inject.Inject
import javax.inject.Named

@PerActivity
class SharedRepositoryImpl @Inject constructor(
    @Named ("context") var context: Context) : SharedRepository {

    val SHAREDNAME = "ISTU_SHARED_PREFERENCES"

    var sharedPreferences : SharedPreferences = context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE)

    override fun setNewsHistorySize(size : Int) {
        sharedPreferences.edit().putInt("historySize", size).apply()
    }

    override fun getNewsHistorySize() : Int {
        return sharedPreferences.getInt("historySize",0)
    }

    override fun setToken(login: LoginResponse) {
            sharedPreferences.apply {
                edit().putString("token", login.getAccessToken()).apply()
                edit().putString("token-type", login.getTokenType()).apply()
            }
    }

    override fun getToken() : String? {
        val token = sharedPreferences.getString("token", null)
        val tokenType = sharedPreferences.getString("token-type", null)

        return if (token == null || tokenType == null) {
            null
        } else "$tokenType $token"
    }
}