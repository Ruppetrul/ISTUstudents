package com.chistoedet.android.istustudents.network.response.chats

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ChatsResponse {

    @SerializedName("chats")
    @Expose
    var chats: Chats ?= null

}