package com.chistoedet.android.istustudents

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.chistoedet.android.core.remote.istu.ISTUProvider
import com.chistoedet.android.istustudents.di.App
import com.chistoedet.android.istustudents.di.SharedRepositoryImpl
import com.chistoedet.android.istustudents.network.response.user.UserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private val TAG = MainActivityViewModel::class.simpleName
class MainActivityViewModel(application: Application) : AndroidViewModel(application) {


    lateinit var api : ISTUProvider

    var shared: SharedRepositoryImpl = SharedRepositoryImpl(application)

    private var app = (application as App)

   // var component : ActivityComponent = DaggerActivityComponent.factory().create(application)

    var userLiveData = MutableLiveData<UserResponse>()

    private val token : String?

    init {
    //    component.inject(this)
        token = shared.getToken()
    }

    fun logout() {
        CoroutineScope(Dispatchers.Main).launch {
           /* component.getApiService().testLogout(token!!).let {
                if (!it.body()?.message.isNullOrEmpty()) {
                    app.logout()
                    val context = app.baseContext
                    val intent = Intent(context,SplashActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                }
            }*/
        }

    }

}