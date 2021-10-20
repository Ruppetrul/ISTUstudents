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
import com.chistoedet.android.istustudents.network.response.user.UserResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private val TAG = LoginViewModel::class.simpleName
class LoginViewModel constructor(application: Application): AndroidViewModel(application) {

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
        checkTokenRelevance()
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
                    if (it.code() == 200 && it.body()?.getUserId() != null) {
                        var user = getUserFromToken("${it.body()?.getTokenType()} ${it.body()?.getAccessToken()}")
                        if (user == null) {
                            //TODO show error
                        } else {
                            app.saveToken(it.body()!!)
                            app.setUser(user).let {
                                callbacks?.onMain()
                            }

                        }

                    } else {

                    }
                }
            } catch (ex: Exception) {
                Log.d(TAG, "onViewCreated: ${ex.message}")

                // TODO показ ошибки соединения
            }

        }
    }

    private suspend fun getUserFromToken(token: String) : UserResponse? {

        token.let { it ->
            component.getApiService().testUser(it).let {
                return if (it.code() == 200 && it.body()?.getId() != null) it.body()
                else null
            }
        }

    }

    private fun checkTokenRelevance() {
        GlobalScope.launch {
            app.getToken()?.apply {
                Log.d(TAG, "checkTokenRelevance: $this")
                    getUserFromToken(this)?.let {
                            app.setUser(it)
                            callbacks?.apply {
                                    this.onMain()
                                }
                            }
                        }
                    }

                }
            }