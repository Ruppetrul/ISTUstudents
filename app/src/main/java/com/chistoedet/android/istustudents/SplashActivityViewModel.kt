package com.chistoedet.android.istustudents

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.chistoedet.android.core.remote.istu.ISTUProvider
import com.chistoedet.android.istustudents.di.ActivityComponent
import com.chistoedet.android.istustudents.di.App
import com.chistoedet.android.istustudents.di.DaggerActivityComponent
import com.chistoedet.android.istustudents.di.SharedRepositoryImpl
import com.chistoedet.android.istustudents.network.response.user.UserResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class TokenState {
    class CheckingState: TokenState()
    class IsRelevance: TokenState()
    class IsNotRelevance: TokenState()
    class ConnectionError: TokenState()
}

class SplashActivityViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var api : ISTUProvider

    @Inject
    lateinit var shared: SharedRepositoryImpl

    private var app = (application as App)

    var component : ActivityComponent = DaggerActivityComponent.factory().create(application)

    val state = MutableLiveData<TokenState>().apply {
        postValue(TokenState.CheckingState())
    }

    init {
        component.inject(this)
        checkTokenRelevance()
    }

    private suspend fun getUserFromToken(token: String) : UserResponse? {
        token.let { it ->

            // from test
            api.fetchChats(token)
            api.fetchStudent(token)
            api.fetchSession(token)

            api.fetchUser(it).let {
                return if (it.code() == 200 && it.body()?.getId() != null) it.body()
                else null
            }

        }
    }

    fun checkTokenRelevance() {
        state.postValue(TokenState.CheckingState())
        GlobalScope.launch {
            try {
                val token =  shared.getToken()
                if (token == null) state.postValue(TokenState.IsNotRelevance())
                else {
                    val user = getUserFromToken(token)
                    if (user?.getId() != null) {
                        app.setUser(user)
                        state.postValue(TokenState.IsRelevance())
                        /*callbacks?.apply {
                            this.onMain()
                        }*/
                    } else
                    {
                        state.postValue(TokenState.IsNotRelevance())
                        //state.postValue(LoginState.InputState())
                    }
                }
            } catch (e : Exception) {
                state.postValue(TokenState.ConnectionError())
            }

        }
    }

}