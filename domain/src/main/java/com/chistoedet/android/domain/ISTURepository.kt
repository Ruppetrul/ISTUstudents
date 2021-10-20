package com.chistoedet.android.domain
import com.chistoedet.android.istustudents.network.response.login.LoginResponse
import kotlinx.coroutines.Deferred

interface ISTURepository {

    suspend fun fetchLogin(): Deferred<LoginResponse>

}