package com.chistoedet.android.istustudents.network.response.chats

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Staff {
    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("user_id")
    @Expose
    private var userId: Int? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("family")
    @Expose
    private var family: String? = null

    @SerializedName("patronymic")
    @Expose
    private var patronymic: String? = null

    @SerializedName("photo")
    @Expose
    private var photo: String? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getUserId(): Int? {
        return userId
    }

    fun setUserId(userId: Int?) {
        this.userId = userId
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getFamily(): String? {
        return family
    }

    fun setFamily(family: String?) {
        this.family = family
    }

    fun getPatronymic(): String? {
        return patronymic
    }

    fun setPatronymic(patronymic: String?) {
        this.patronymic = patronymic
    }

    fun getPhoto(): String? {
        return photo
    }

    fun setPhoto(photo: String?) {
        this.photo = photo
    }

}