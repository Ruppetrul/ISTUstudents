package com.chistoedet.android.istustudents

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
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

    var userLiveData = MutableLiveData<UserResponse>()
    var app : App
    val token : String?

    init {
        component.inject(this)
        app = (application as App)
        token = app.getToken()
        }


    fun updateUser() {

        var user = token?.let {
            CoroutineScope(Dispatchers.Main).launch {
                component.getApiService().testUser(it).let {
                    it.code().apply {
                        when (this) {
                            200 -> {
                                userLiveData.postValue(it.body())
                                app.setUser(it.body()!!)
                            }
                            else -> {
                                Log.e(TAG, "updateUser: update error")
                            }
                        }
                    }


            }
        }
    }
}

    fun logout() {
        CoroutineScope(Dispatchers.Main).launch {
            component.getApiService().testLogout(token!!).let {
                if (!it.body()?.message.isNullOrEmpty()) {
                    app.logout()
                    val context = app.baseContext
                    context.startActivity(Intent(context,SplashActivity::class.java))
                }
            }
        }

    }
}