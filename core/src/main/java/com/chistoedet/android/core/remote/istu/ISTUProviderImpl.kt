package com.chistoedet.android.core.remote.istu

import com.chistoedet.android.core.RetrofitFactory
import com.chistoedet.android.istustudents.network.requests.LoginRequest
import com.chistoedet.android.istustudents.network.response.chats.ChatsChatResponse
import com.chistoedet.android.istustudents.network.response.chats.ChatsResponse
import com.chistoedet.android.istustudents.network.response.login.LoginResponse
import com.chistoedet.android.istustudents.network.response.logout.LogoutResponse
import com.chistoedet.android.istustudents.network.response.user.UserResponse
import okhttp3.ResponseBody
import retrofit2.Response

class ISTUProviderImpl : ISTUProvider {

    override suspend fun fetchLogin(loginRequest: LoginRequest): Response<LoginResponse> {
        return RetrofitFactory.getApiService().testLogin(loginRequest)
    }

    override suspend fun fetchUser(token: String): Response<UserResponse> {
        return RetrofitFactory.getApiService().testUser(token)
    }

    override suspend fun fetchLogout(token: String): Response<LogoutResponse> {
        return RetrofitFactory.getApiService().testLogout(token)
    }

    override suspend fun fetchChats(token: String): Response<ChatsResponse> {
        return RetrofitFactory.getApiService().testChats(token)
    }

    override suspend fun fetchChats(token: String, chat: Int): Response<ChatsChatResponse> {
        return RetrofitFactory.getApiService().testChats(token, chat)
    }

    override suspend fun fetchStaff(token: String): Response<ResponseBody> {
        return RetrofitFactory.getApiService().testStaff(token)
    }

    override suspend fun fetchStudent(token: String): Response<ResponseBody> {
        return RetrofitFactory.getApiService().testStudent(token)
    }

    override suspend fun fetchSession(token: String): Response<ResponseBody> {
        return RetrofitFactory.getApiService().testSession(token)
    }

}