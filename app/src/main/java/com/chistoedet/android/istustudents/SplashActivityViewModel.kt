package com.chistoedet.android.istustudents

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.chistoedet.android.core.remote.ISTUProvider
import com.chistoedet.android.core.remote.ISTUProviderImpl
import com.chistoedet.android.istustudents.di.App
import com.chistoedet.android.istustudents.network.response.user.UserResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

sealed class TokenState {
    class CheckingState: TokenState()
    class IsRelevance: TokenState()
    class IsNotRelevance: TokenState()
    class ConnectionError: TokenState()
}

class SplashActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val api : ISTUProvider = ISTUProviderImpl()

    private var app = (application as App)

    val state = MutableLiveData<TokenState>().apply {
        postValue(TokenState.CheckingState())
    }


    init {

    }

    private suspend fun getUserFromToken(token: String) : UserResponse? {
        token.let { it ->
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
                val token = app.getToken()
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