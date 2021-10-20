package com.chistoedet.android.istustudents.network.response.chats

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ChatsChatResponse {
        @SerializedName("messages")
        @Expose
        var messages: List<Message>? = null
    }