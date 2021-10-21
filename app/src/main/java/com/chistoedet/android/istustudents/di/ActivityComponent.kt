package com.chistoedet.android.istustudents.di

import com.chistoedet.android.core.ISTUService
import com.chistoedet.android.istustudents.MainActivity
import com.chistoedet.android.istustudents.MainActivityViewModel
import com.chistoedet.android.istustudents.ui.main.messenger.chat.ChatViewModel
import com.chistoedet.android.istustudents.ui.main.messenger.list.ContactListViewModel

import com.chistoedet.android.istustudents.ui.splash.login.LoginViewModel
import dagger.Component
import javax.inject.Singleton

//@PerActivity
@Singleton
@Component(modules = [NetworkModule::class, DataModule::class])
interface ActivityComponent {

    fun inject(loginFragmentViewModel: LoginViewModel)

    fun inject(mainViewModel: MainActivityViewModel)
    fun inject(chatViewModel: ChatViewModel)
    fun inject(messengerViewModel: ContactListViewModel)

    fun inject(mainActivity: MainActivity)
    fun getApiService() : ISTUService

   // fun getPrefManager() : SharedPreferences

    @Component.Builder
    interface Builder {
        fun build() : ActivityComponent
        fun dataModule(dataModule: DataModule) : Builder
    }
/*    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance @Named ("context") context : Context
        ): ActivityComponent
    }*/

}