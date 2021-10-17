package com.chistoedet.android.istustudents.network.response.chats

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Chats {
    @SerializedName("students")
    @Expose
    var students: List<Any>? = null

    @SerializedName("staffs")
    @Expose
    var staffs: List<Staffs>? = null
}