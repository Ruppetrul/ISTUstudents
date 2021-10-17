package com.chistoedet.android.istustudents.network.response.chats

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Staffs {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("staff_id")
    @Expose
    var staffId: Int? = null

    @SerializedName("student_id")
    @Expose
    var studentId: Int? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

    @SerializedName("unread_messages_count")
    @Expose
    var unreadMessagesCount: Int? = null

    @SerializedName("staff")
    @Expose
    var staff: Staff? = null

    @SerializedName("latest_message")
    @Expose
    var latestMessage: LatestMessage? = null


}