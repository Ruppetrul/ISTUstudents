package com.chistoedet.android.istustudents.network.response.chats

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class To {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null
}