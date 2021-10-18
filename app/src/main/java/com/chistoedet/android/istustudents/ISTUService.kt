package com.chistoedet.android.istustudents


import com.chistoedet.android.istustudents.network.response.login.LoginResponse
import com.chistoedet.android.istustudents.network.requests.LoginRequest
import com.chistoedet.android.istustudents.network.response.chats.ChatsChatResponse
import com.chistoedet.android.istustudents.network.response.chats.ChatsResponse
import com.chistoedet.android.istustudents.network.response.user.UserResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ISTUService {

    @GET("/")
    fun testRequest(): Call<ResponseBody>

    @POST("login")
    suspend fun testLogin(@Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @GET("user")
    suspend fun testUser(
        @Header("Authorization") token: String
    ): Response<UserResponse>

    @GET("session")
    suspend fun testSession(
        @Header("Authorization") token: String
    ): Response<ResponseBody>

    @GET("student")
    suspend fun testStudent(
        @Header("Authorization") token: String
    ): Response<ResponseBody>

    @GET("staff")
    suspend fun testStaff(
        @Header("Authorization") token: String
    ): Response<ResponseBody>

    @GET("chats")
    suspend fun testChats(
        @Header("Authorization") token: String
    ): Response<ChatsResponse>

    @GET("chats/{chat}")
    suspend fun testChats(
        @Header("Authorization") token: String,
        @Path("chat") chat: Int
    ): Response<ChatsChatResponse>

}