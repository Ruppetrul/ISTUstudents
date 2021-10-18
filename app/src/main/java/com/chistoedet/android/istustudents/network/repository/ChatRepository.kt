package com.chistoedet.android.istustudents.network.repository

import android.util.Log
import com.chistoedet.android.istustudents.ISTUService
import com.chistoedet.android.istustudents.di.ActivityComponent
import com.chistoedet.android.istustudents.di.App
import com.chistoedet.android.istustudents.di.DaggerActivityComponent
import com.chistoedet.android.istustudents.di.DataModule
import com.chistoedet.android.istustudents.network.response.chats.ChatsChatResponse
import retrofit2.Response

import javax.inject.Inject

class ChatRepository(var api :ISTUService,var token: String) {

    suspend fun getChatHistory(chat: Int) : Response<ChatsChatResponse> {
        return api.testChats(token, chat)
    }

}