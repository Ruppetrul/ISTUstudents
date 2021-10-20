package com.chistoedet.android.domain

import com.chistoedet.android.istustudents.network.response.login.LoginResponse
import kotlinx.coroutines.Deferred

class ISTURepositoryImpl()
    : ISTURepository {

    /*suspend fun getChatHistory(chat: Int) : Response<ChatsChatResponse> {
        return api.testChats(token, chat)
    }*/

    override suspend fun fetchLogin(): Deferred<LoginResponse> {
        TODO("Not yet implemented")
    }

}