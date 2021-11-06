package com.chistoedet.android.istustudents.ui.main.messenger.chat

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chistoedet.android.core.remote.istu.ISTUProvider
import com.chistoedet.android.istustudents.di.ActivityComponent
import com.chistoedet.android.istustudents.di.DaggerActivityComponent
import com.chistoedet.android.istustudents.di.SharedRepositoryImpl
import com.chistoedet.android.istustudents.network.response.chats.Message
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


private val TAG = ChatState::class.java.simpleName
sealed class ChatState {
    class LoadingState: ChatState()
    class ConnectionError: ChatState()
    class NoMessageState: ChatState()
    class ActiveChatState: ChatState()
    class ErrorState<T>(val message: T): ChatState()
}

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var api : ISTUProvider

    @Inject
    lateinit var shared: SharedRepositoryImpl

    var component : ActivityComponent = DaggerActivityComponent.factory().create(application)

    var chatHistory = MutableLiveData<MutableList<Message>>()

    val state = MutableLiveData<ChatState>().apply {
        postValue(ChatState.LoadingState())
    }

    var token : String

    init {
        component.inject(this)
        token = shared.getToken()!!
    }

    fun updateChatHistory(chat: Int) {
        viewModelScope.launch {
            try {
                api.fetchChats(token,chat).let {
                    when (it.code()) {
                        200 -> {
                            state.postValue(ChatState.ActiveChatState())
                            it.body()?.messages.apply {
                                if (this != null && this.isNotEmpty()) {
                                    state.postValue(ChatState.ActiveChatState())
                                    chatHistory.postValue(this.toMutableList())
                                } else {
                                    state.postValue(ChatState.NoMessageState())
                                }
                            }


                        } else -> {
                        state.postValue(ChatState.ErrorState("Произошла ошибка ${it.code()}"))
                    }
                    }
                }
            } catch (e: Exception) {
                state.postValue(ChatState.ConnectionError())
            } catch (e: IOException) {
                Log.d(TAG, "updateChatHistory: IORuntimeException")
            }
           // Log.d(TAG, "updateChatHistory: $chatHistory")
        }
    }
}