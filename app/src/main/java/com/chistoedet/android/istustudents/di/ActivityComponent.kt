package com.chistoedet.android.istustudents.di

import android.content.Context
import com.chistoedet.android.core.remote.istu.ISTUProvider
import com.chistoedet.android.istustudents.MainActivity
import com.chistoedet.android.istustudents.MainActivityViewModel
import com.chistoedet.android.istustudents.SplashActivityViewModel
import com.chistoedet.android.istustudents.services.news.NewsPollingWorker
import com.chistoedet.android.istustudents.ui.main.messenger.chat.ChatViewModel
import com.chistoedet.android.istustudents.ui.main.messenger.list.ContactListViewModel
import com.chistoedet.android.istustudents.ui.main.profile.ProfileViewModel
import com.chistoedet.android.istustudents.ui.splash.login.LoginViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named
import javax.inject.Singleton

//@PerActivity
@Singleton
@Component(modules = [NetworkModule::class])
interface ActivityComponent {

    fun inject(loginFragmentViewModel: LoginViewModel)

    fun inject(mainViewModel: MainActivityViewModel)
    fun inject(chatViewModel: ChatViewModel)
    fun inject(messengerViewModel: ContactListViewModel)
    fun inject(newsPollingWorker: NewsPollingWorker)

    fun inject(mainActivity: MainActivity)
    fun inject(splashActivityViewModel: SplashActivityViewModel)
    fun inject(profileViewModel: ProfileViewModel)

    fun getISTUProvider() : ISTUProvider

    fun getPreferenceRepository() : SharedRepositoryImpl


    /*@Component.Builder
    interface Builder {
        fun build() : ActivityComponent
        @BindsInstance
        fun context(context: Context) : Builder
    }*/

    @Component.Factory
    @Singleton
    interface Factory {
        fun create(
            @BindsInstance @Named("context") context : Context
        ): ActivityComponent
    }

}