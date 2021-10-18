package com.chistoedet.android.istustudents.ui.main.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.chistoedet.android.istustudents.UserInformation
import com.chistoedet.android.istustudents.di.App

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private var callbacks: Callbacks? = null

    private val app = (application as App)

    // TODO mutable данные

    private val passport = MutableLiveData<String>()
    private val inn = MutableLiveData<String>()
    private val snils = MutableLiveData<String>()

    var userInformation = MutableLiveData<UserInformation>()

    interface Callbacks {
        fun onSave()
    }

    init {
        updateInfo()
        }

    private fun updateInfo() {

        val userInfo = getInformation()
        userInformation.postValue(userInfo)
        passport.postValue(userInfo.passport)
        inn.postValue(userInfo.inn)
        snils.postValue(userInfo.snils)

    }

    private fun getInformation() : UserInformation {
        return app.getUserInformation()
    }

    fun saveInfo(saveInformation: UserInformation) {
        app.saveUserInfo(saveInformation)
        updateInfo()
    }

}