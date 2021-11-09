package com.chistoedet.android.istustudents.di

import com.chistoedet.android.istustudents.UserInformation
import com.chistoedet.android.istustudents.network.response.login.LoginResponse
import com.chistoedet.android.istustudents.network.response.user.UserResponse

interface SharedRepository {

    fun setNewsHistorySize(size : Int)

    fun getNewsHistorySize() : Int

    fun getToken() : String?

    fun setToken(login: LoginResponse)

    fun getLastLogin() : String?

    fun saveLastLogin(email: String)
    // TODO перенести в репозиторий
    fun getUserInformation(user : UserResponse) : UserInformation

    fun logout()

    fun saveUserInfo(user: UserResponse, saveInformation: UserInformation)

}