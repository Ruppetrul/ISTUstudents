package com.chistoedet.android.istustudents.ui.splash.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.chistoedet.android.core.remote.istu.ISTUProvider
import com.chistoedet.android.core.remote.istu.ISTUProviderImpl
import com.chistoedet.android.istustudents.di.App
import com.chistoedet.android.istustudents.di.SharedRepositoryImpl
import com.chistoedet.android.istustudents.network.requests.LoginRequest
import com.chistoedet.android.istustudents.network.response.user.UserResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


sealed class LoginState {
    class LoggingState: LoginState()
    class LoginSuccessful: LoginState()
    class InputState: LoginState()
    class LoginErrorState(var error: String): LoginState()
    class ErrorState(var error: String): LoginState()
}

private val TAG = LoginViewModel::class.simpleName
class LoginViewModel constructor(application: Application): AndroidViewModel(application) {

    val state = MutableLiveData<LoginState>().apply {
        postValue(LoginState.LoggingState())
    }


    var api : ISTUProvider = ISTUProviderImpl()

    var shared: SharedRepositoryImpl = SharedRepositoryImpl(application)

    private var app = (application as App)

   // var component : ActivityComponent = DaggerActivityComponent.factory().create(application)

    init {
     //   component.inject(this)
    }

    fun getLastLogin() : String? {
        return shared.getLastLogin()
    }

    fun onLogin(email: String, password: String) {
        Log.d(TAG, "onLogin: $email")
        Log.d(TAG, "onLogin: $password")
        val loginBody = LoginRequest()
        loginBody.email = email
        loginBody.password = password

        GlobalScope.launch {
            try {
                 api.fetchLogin(loginBody).let {
                    if (it.code() == 200 && it.body()?.getUserId() != null) {
                        var user = getUserFromToken("${it.body()?.getTokenType()} ${it.body()?.getAccessToken()}")
                        if (user == null) {
                            state.postValue(LoginState.ErrorState("Ошибка подключения"))
                        } else {
                            shared.setToken(it.body()!!)
                            shared.saveLastLogin(loginBody.email!!)
                            app.setUser(user).let {
                                state.postValue(LoginState.LoginSuccessful())
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
            api.fetchUser(it).let {
                return if (it.code() == 200 && it.body()?.getId() != null) it.body()
                else null
            }
        }

    }


}
