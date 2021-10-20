package com.chistoedet.android.istustudents.ui.splash.login

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.chistoedet.android.istustudents.di.ActivityComponent
import com.chistoedet.android.istustudents.di.App
import com.chistoedet.android.istustudents.di.DaggerActivityComponent
import com.chistoedet.android.istustudents.di.DataModule
import com.chistoedet.android.istustudents.network.requests.LoginRequest
import com.chistoedet.android.istustudents.network.response.user.UserResponse
import com.chistoedet.android.istustudents.ui.main.messenger.chat.ChatState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

sealed class LoginState {
    class LoggingState: LoginState()
    class SendingState: LoginState()
    class InputState: LoginState()
    class LoginErrorState(var error: String): LoginState()
    class ErrorState(var error: String): LoginState()
}

private val TAG = LoginViewModel::class.simpleName
class LoginViewModel constructor(application: Application): AndroidViewModel(application) {

    val state = MutableLiveData<LoginState>().apply {
        postValue(LoginState.LoggingState())
    }

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
                            state.postValue(LoginState.ErrorState("Ошибка подключения"))
                        } else {
                            app.saveToken(it.body()!!)
                            app.setUser(user).let {
                                callbacks?.onMain()
                            }
                        }
                    } else {
                        state.postValue(LoginState.LoginErrorState("Неверный логин или пароль"))
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
            val token = app.getToken()
            if (token == null) state.postValue(LoginState.InputState())
             else {
                val user = getUserFromToken(token)
                if (user != null && user.getId() != null) {
                    app.setUser(user)
                    callbacks?.apply {
                        this.onMain()
                    }
                } else
                {
                    state.postValue(LoginState.InputState())
                }
            }
        }
    }
}
