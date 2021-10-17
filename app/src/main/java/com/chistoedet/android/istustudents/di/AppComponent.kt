package com.chistoedet.android.istustudents.di

import android.content.SharedPreferences
import android.provider.ContactsContract
import com.chistoedet.android.istustudents.ISTUService
import com.chistoedet.android.istustudents.network.response.login.LoginResponse
import dagger.Component
import javax.inject.Inject
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
interface AppComponent {

    fun getApiService() : ISTUService

    // getSharedPref() : SharedPreferences

    //fun getMainComponentFactory() : ActivityComponent.Factory

    @Singleton
    @Component.Factory
    interface Factory {
        fun create() : AppComponent
    }



}