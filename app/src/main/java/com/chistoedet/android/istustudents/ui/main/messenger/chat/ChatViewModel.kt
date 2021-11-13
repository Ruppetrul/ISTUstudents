package com.chistoedet.android.istustudents.ui.main.messenger.chat

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chistoedet.android.core.remote.istu.ISTUProvider
import com.chistoedet.android.core.remote.istu.ISTUProviderImpl
import com.chistoedet.android.istustudents.di.SharedRepositoryImpl
import com.chistoedet.android.istustudents.network.response.chats.Message
import com.chistoedet.android.istustudents.network.response.chats.Staffs
import kotlinx.coroutines.launch
import java.io.IOException


private val TAG = ChatState::class.java.simpleName
sealed class ChatState {
    class LoadingState: ChatState()
    class ConnectionError: ChatState()
    class NoMessageState: ChatState()
    class ActiveChatState: ChatState()
    class ErrorState<T>(val message: T): ChatState()
}

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    var api : ISTUProvider = ISTUProviderImpl()

    var shared: SharedRepositoryImpl = SharedRepositoryImpl(application)

  //  var component : ActivityComponent = DaggerActivityComponent.factory().create(application)

    var chatHistory = MutableLiveData<MutableList<Message>>()

    val state = MutableLiveData<ChatState>().apply {
        postValue(ChatState.LoadingState())
    }

    var token : String

    init {
       // component.inject(this)
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

    fun sendMessage(user: Staffs, message: String) {
        viewModelScope.launch {
        user.staffId?.let { api.sendMessage(token, it, message) }
        }
    }
}