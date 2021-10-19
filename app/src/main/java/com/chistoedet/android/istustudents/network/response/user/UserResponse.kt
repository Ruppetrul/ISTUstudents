package com.chistoedet.android.istustudents.network.response.user

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class UserResponse
{
    @SerializedName("id")
    @Expose
    private var id: Int? = null


    @SerializedName("fio")
    @Expose
    private var fio: String? = null

    @SerializedName("fio_full")
    @Expose
    private var fioFull: FioFull? = null

    @SerializedName("email")
    @Expose
    private var email: String? = null

    @SerializedName("isPPS")
    @Expose
    private var isPPS: Boolean? = null

    @SerializedName("staff")
    @Expose
    private var staff: Any? = null

    @SerializedName("student")
    @Expose
    private var student: List<Student>? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getFio(): String? {
        return fio
    }

    fun setFio(fio: String?) {
        this.fio = fio
    }

    fun getFioFull(): FioFull? {
        return fioFull
    }

    fun setFioFull(fioFull: FioFull?) {
        this.fioFull = fioFull
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String?) {
        this.email = email
    }

    fun getIsPPS(): Boolean? {
        return isPPS
    }

    fun setIsPPS(isPPS: Boolean?) {
        this.isPPS = isPPS
    }

    fun getStaff(): Any? {
        return staff
    }

    fun setStaff(staff: Any?) {
        this.staff = staff
    }

    fun getStudent(): List<Student?>? {
        return student
    }

    fun setStudent(student: List<Student>?) {
        this.student = student
    }

}