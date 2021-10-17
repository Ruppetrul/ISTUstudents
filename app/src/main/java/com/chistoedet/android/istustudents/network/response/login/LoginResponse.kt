package com.chistoedet.android.istustudents.network.response.login

import com.squareup.moshi.Json
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("user_id")
    @Expose
    private var userId: Int? = null

    @SerializedName("isVerify")
    @Expose
    private var isVerify: Boolean? = null

    @SerializedName("access_token")
    @Expose
    private var accessToken: String? = null

    @SerializedName("token_type")
    @Expose
    private var tokenType: String? = null

    @SerializedName("expires_at")
    @Expose
    private var expiresAt: String? = null

    fun getUserId(): Int? {
        return userId
    }

    fun setUserId(userId: Int?) {
        this.userId = userId
    }

    fun getIsVerify(): Boolean? {
        return isVerify
    }

    fun setIsVerify(isVerify: Boolean?) {
        this.isVerify = isVerify
    }

    fun getAccessToken(): String? {
        return accessToken
    }

    fun setAccessToken(accessToken: String?) {
        this.accessToken = accessToken
    }

    fun getTokenType(): String? {
        return tokenType
    }

    fun setTokenType(tokenType: String?) {
        this.tokenType = tokenType
    }

    fun getExpiresAt(): String? {
        return expiresAt
    }

    fun setExpiresAt(expiresAt: String?) {
        this.expiresAt = expiresAt
    }
}