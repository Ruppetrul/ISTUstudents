package com.chistoedet.android.istustudents.network.response.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Student {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("group")
    @Expose
    var group: String? = null

    @SerializedName("course")
    @Expose
    var course: Int? = null

    @SerializedName("qualification")
    @Expose
    var qualification: String? = null

    @SerializedName("profile")
    @Expose
    var profile: String? = null

    @SerializedName("speciality")
    @Expose
    var speciality: String? = null

    @SerializedName("education_form")
    @Expose
    var educationForm: String? = null

    @SerializedName("department")
    @Expose
    var department: String? = null
}