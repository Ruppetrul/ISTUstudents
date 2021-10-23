package com.chistoedet.android.istustudents.network.response.chats

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LatestMessage : Serializable {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("chat_id")
    @Expose
    var chatId: Int? = null

    @SerializedName("from")
    @Expose
    var from: Int? = null

    @SerializedName("to")
    @Expose
    var to: Int? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("isRead")
    @Expose
    var isRead: Boolean? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

}