package com.chistoedet.android.istustudents.network.response.logout

import com.chistoedet.android.istustudents.network.response.chats.Message
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LogoutResponse {

    @SerializedName("message")
    @Expose
    var message: String? = null

}