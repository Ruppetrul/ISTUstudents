package com.chistoedet.android.istustudents.network.response.user

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class FioFull
{

    @SerializedName("family")
    @Expose
    private var family: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("patronymic")
    @Expose
    private var patronymic: String? = null

    fun getFamily(): String? {
        return family
    }

    fun setFamily(family: String?) {
        this.family = family
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getPatronymic(): String? {
        return patronymic
    }

    fun setPatronymic(patronymic: String?) {
        this.patronymic = patronymic
    }


}