package com.chistoedet.android.istustudents

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chistoedet.android.core.remote.istu.ISTUProviderImpl
import com.chistoedet.android.istustudents.di.App
import com.chistoedet.android.istustudents.di.SharedRepositoryImpl
import com.chistoedet.android.istustudents.network.response.user.UserResponse
import com.vk.api.sdk.VK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private val TAG = MainActivityViewModel::class.simpleName
class MainActivityViewModel(application: Application) : AndroidViewModel(application) {


    var api : ISTUProviderImpl = ISTUProviderImpl()

    var shared: SharedRepositoryImpl = SharedRepositoryImpl(application)

    private var app = (application as App)

   // var component : ActivityComponent = DaggerActivityComponent.factory().create(application)

    var userLiveData = MutableLiveData<UserResponse>()

    private val token : String? = shared.getToken()

    init {
    //    component.inject(this)


        viewModelScope.launch {
            app.getUser()?.let {
                if (token != null) {
                    app.getUser()!!.getId()?.let { it1 ->
                      //  api.fetchSession(token, it1)
                      //  api.fetchStudent(token, it1)
                    }
                }
            }
        }

    }


    fun logout() {
        CoroutineScope(Dispatchers.Main).launch {
            api.fetchLogout(token!!).let {
                if (!it.body()?.message.isNullOrEmpty()) {
                    shared.logout()
                    val context = app.baseContext
                    val intent = Intent(context, SplashActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)

                }
            }.let {
                VK.logout()
            }
        }

    }

}