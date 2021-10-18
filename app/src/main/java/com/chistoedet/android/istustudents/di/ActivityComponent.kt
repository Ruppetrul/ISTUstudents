package com.chistoedet.android.istustudents.di

import com.chistoedet.android.istustudents.ISTUService
import com.chistoedet.android.istustudents.MainActivityViewModel
import com.chistoedet.android.istustudents.network.repository.ChatRepository
import com.chistoedet.android.istustudents.ui.main.messenger.chat.ChatViewModel
import com.chistoedet.android.istustudents.ui.main.messenger.list.MessengerViewModel
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
    fun inject(messengerViewModel: MessengerViewModel)
    fun inject(chatRepository: ChatRepository)

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