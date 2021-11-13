package com.chistoedet.android.core.remote.istu

import com.chistoedet.android.istustudents.network.requests.LoginRequest
import com.chistoedet.android.istustudents.network.response.chats.ChatsChatResponse
import com.chistoedet.android.istustudents.network.response.chats.ChatsResponse
import com.chistoedet.android.istustudents.network.response.login.LoginResponse
import com.chistoedet.android.istustudents.network.response.logout.LogoutResponse
import com.chistoedet.android.istustudents.network.response.user.UserResponse
import okhttp3.ResponseBody
import retrofit2.Response

interface ISTUProvider {

    suspend fun fetchLogin(loginRequest: LoginRequest) : Response<LoginResponse>

    suspend fun fetchUser(token: String) : Response<UserResponse>

    suspend fun fetchLogout(token: String) : Response<LogoutResponse>

    suspend fun fetchChats(token: String) : Response<ChatsResponse>

    suspend fun fetchChats(token: String, chat: Int) : Response<ChatsChatResponse>

    suspend fun fetchStaff(token: String) : Response<ResponseBody>

    suspend fun fetchStudent(token: String, id: Int) : Response<ResponseBody>

    suspend fun fetchSession(token: String, id: Int) : Response<ResponseBody>

    suspend fun sendMessage(token: String, id: Int, message: String) : Response<ResponseBody>



}