package com.chistoedet.android.istustudents

import android.app.Application
import android.util.Log
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
    var app : Application

    init {
        component.inject(this)
        app = application
         }


    fun updateUser() {
        val app = (app as App)
        val token = app.getToken()
        var user = token?.let {
            CoroutineScope(Dispatchers.Main).launch {
                component.getApiService().testUser(it).let {
                    it.code().apply {
                        when (this) {
                            200 -> {
                                userLiveData.postValue(it.body())
                                app. setUser(it.body()!!)
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
}