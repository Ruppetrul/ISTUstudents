package com.chistoedet.android.istustudents.di

import com.chistoedet.android.istustudents.network.response.login.LoginResponse

interface SharedRepository {

    fun setNewsHistorySize(size : Int)

    fun getNewsHistorySize() : Int

    fun getToken() : String?

    fun setToken(login: LoginResponse)
}