package com.chistoedet.android.istustudents.ui.main.messenger.chat

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.chistoedet.android.istustudents.di.App
import com.chistoedet.android.istustudents.network.repository.ChatRepository
import com.chistoedet.android.istustudents.network.response.chats.Message
import com.chistoedet.android.istustudents.network.response.chats.Staffs
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Appendable

private val TAG = ChatState::class.java.simpleName
sealed class ChatState {
    class LoadingState: ChatState()
    class SendingState: ChatState()
    class NoMessageState: ChatState()
    class ActiveChatState: ChatState()
    class ErrorState<T>(val message: T): ChatState()
}

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private var repository : ChatRepository
    private var app = (application as App)

    var chatHistory = MutableLiveData<MutableList<Message>>()

    val state = MutableLiveData<ChatState>().apply {
        postValue(ChatState.LoadingState())
    }

    init {
        repository = ChatRepository(app.getLoginService(),app.getToken())

    }

    fun updateChatHistory(chat: Int) {
        viewModelScope.launch {
            repository.getChatHistory(chat).let {
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