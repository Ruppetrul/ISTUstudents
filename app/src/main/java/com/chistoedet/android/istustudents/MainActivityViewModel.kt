package com.chistoedet.android.istustudents

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.chistoedet.android.istustudents.di.App
import com.chistoedet.android.istustudents.di.DaggerActivityComponent
import com.chistoedet.android.istustudents.di.DataModule
import com.chistoedet.android.istustudents.network.response.user.UserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch




private val TAG = MainActivityViewModel::class.simpleName
class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private var component = DaggerActivityComponent.builder().dataModule(DataModule(application)).build()
    //private val vkApi : VkProvider = VkProviderImpl(application.baseContext)

    var userLiveData = MutableLiveData<UserResponse>()
    var app : App
    val token : String?

    init {
        component.inject(this)
        app = (application as App)
        token = app.getToken()

        //checkVkSuccess()
        }


   /* suspend fun checkVkSuccess(): Boolean {

        //return vkApi.getAccessBool()

    }*/



    fun logout() {
        CoroutineScope(Dispatchers.Main).launch {
            component.getApiService().testLogout(token!!).let {
                if (!it.body()?.message.isNullOrEmpty()) {
                    app.logout()
                    val context = app.baseContext
                    val intent = Intent(context,SplashActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                }
            }
        }

    }

    fun vkAutorization() {

    }
}