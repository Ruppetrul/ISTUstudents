package com.chistoedet.android.istustudents.ui.splash.login

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.chistoedet.android.istustudents.di.ActivityComponent
import com.chistoedet.android.istustudents.di.App
import com.chistoedet.android.istustudents.di.DaggerActivityComponent
import com.chistoedet.android.istustudents.di.DataModule
import com.chistoedet.android.istustudents.network.requests.LoginRequest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private val TAG = LoginViewModel::class.simpleName
class LoginViewModel(application: Application): AndroidViewModel(application) {

    private var component : ActivityComponent = DaggerActivityComponent
        .builder().dataModule(DataModule(application)).build()

    //@Inject lateinit var apiService: ISTUService
    private var app = (application as App)
    private var apiService = (application as App)
        .getLoginService()

    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onMain()
    }

    init {
        component.inject(this)
        getTokenRelevance()
    }

    fun test (context: Context) {
        callbacks = context as Callbacks
    }

    fun getLastLogin() : String? {
        return app.getLastLogin()
    }

    override fun onCleared() {
        super.onCleared()
        callbacks = null
    }


    fun onLogin(email: String, password: String) {

        val loginBody = LoginRequest()
        loginBody.email = email
        loginBody.password = password

        GlobalScope.launch {

            try {
                apiService.testLogin(loginBody).let {

                    when(it.code()) {
                        200 -> {
                            app.setLogin(it.body()!!)
                            app.saveLastLogin(loginBody.email)
                            //activity.getSharedPreferences()
                            // TODO сделать состояние
                                getTokenRelevance()
                            }
                        else -> {

                            //Log.d(TAG, "случилась ошибка ${it.errorBody()?.string()}")

                        }

                    }

                }
            } catch (ex: Exception) {
                Log.d(TAG, "onViewCreated: ${ex.message}")

                // TODO показ ошибки соединения
            }

        }
    }

    private fun getTokenRelevance() {
        GlobalScope.launch {
            val token = app.getToken()

            token?.let { it ->
                component.getApiService().testUser(it).let {
                    if (it.code() == 200) {
                        app.setUser(it.body()!!).let {
                            callbacks?.onMain()
                        }

                    }
                }
            }
            }
        }
    }

