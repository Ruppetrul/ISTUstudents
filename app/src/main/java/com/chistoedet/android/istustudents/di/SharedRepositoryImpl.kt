package com.chistoedet.android.istustudents.di

import android.content.Context
import android.content.SharedPreferences
import com.chistoedet.android.istustudents.UserInformation
import com.chistoedet.android.istustudents.network.response.login.LoginResponse
import com.chistoedet.android.istustudents.network.response.user.UserResponse

//@PerActivity
class SharedRepositoryImpl constructor(
     var context: Context) : SharedRepository {

    val SHAREDNAME = "ISTU_SHARED_PREFERENCES"

    var sharedPreferences : SharedPreferences = context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE)

    override fun setNewsHistorySize(size : Int) {
        sharedPreferences.edit().putInt("historySize", size).apply()
    }

    override fun getNewsHistorySize() : Int {
        return sharedPreferences.getInt("historySize",0)
    }

    override fun setToken(login: LoginResponse) {
            sharedPreferences.edit().putString("token", login.getAccessToken()).apply()
            sharedPreferences.edit().putString("token-type", login.getTokenType()).apply()
            }


    override fun getToken() : String? {
        val token = sharedPreferences.getString("token", null)
        val tokenType = sharedPreferences.getString("token-type", null)

        return if (token == null || tokenType == null) {
            null
        } else "$tokenType $token"
    }

    override fun getLastLogin() : String? {
        return sharedPreferences.getString("lastLogin", "")
    }

    override fun saveLastLogin(email: String) {
        sharedPreferences.edit().putString("lastLogin", email).apply()
    }

    override fun getUserInformation(user : UserResponse) : UserInformation {
        val passport = sharedPreferences.getString("${user?.getId()}_passport", null)
        val inn = sharedPreferences.getString("${user?.getId()}_inn", null)
        val snils = sharedPreferences.getString("${user?.getId()}_snils", null)

        val userInfo = UserInformation()
        userInfo.passport = passport
        userInfo.inn = inn
        userInfo.snils = snils

        return userInfo
    }

    override fun logout() {
        sharedPreferences.edit().remove("token").apply()
        sharedPreferences.edit().remove("token-type").apply()
    }

    override fun saveUserInfo(user : UserResponse,saveInformation: UserInformation) {
        sharedPreferences.edit().putString("${user?.getId()}_passport",saveInformation.passport).apply()
        sharedPreferences.edit().putString("${user?.getId()}_inn", saveInformation.inn).apply()
        sharedPreferences.edit().putString("${user?.getId()}_snils", saveInformation.snils).apply()
    }
}