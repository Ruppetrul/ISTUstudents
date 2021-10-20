package com.chistoedet.android.istustudents.ui.main.messenger.chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chistoedet.android.core.remote.ISTUProvider
import com.chistoedet.android.core.remote.ISTUProviderImpl
import com.chistoedet.android.istustudents.di.App
import com.chistoedet.android.istustudents.network.response.chats.Message
import kotlinx.coroutines.launch

private val TAG = ChatState::class.java.simpleName
sealed class ChatState {
    class LoadingState: ChatState()
    class SendingState: ChatState()
    class NoMessageState: ChatState()
    class ActiveChatState: ChatState()
    class ErrorState<T>(val message: T): ChatState()
}

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val api : ISTUProvider = ISTUProviderImpl()
    private var app = (application as App)

    var chatHistory = MutableLiveData<MutableList<Message>>()

    val state = MutableLiveData<ChatState>().apply {
        postValue(ChatState.LoadingState())
    }

    var token : String

    init {
        token = app.getToken()!!
    }

    fun updateChatHistory(chat: Int) {
        viewModelScope.launch {
            api.fetchChats(token,chat).let {
                when (it.code()) {
                    200 -> {
                        state.postValue(ChatState.ActiveChatState())

                        chatHistory.postValue(it.body()?.messages?.toMutableList()!!)

                    }
                }
            }

           // Log.d(TAG, "updateChatHistory: $chatHistory")
        }
    }


    
}