package com.chistoedet.android.istustudents.network.response.chats

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Message {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("chat_id")
    @Expose
    var chatId: Int? = null

    @SerializedName("from")
    @Expose
    var from: From? = null

    @SerializedName("to")
    @Expose
    var to: To? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("isread")
    @Expose
    var isread: Boolean? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null
}