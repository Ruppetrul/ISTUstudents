package com.chistoedet.android.istustudents.di


import com.chistoedet.android.istustudents.ISTUService
import dagger.Component
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