package com.chistoedet.android.istustudents.ui.main.profile

import android.app.Application
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chistoedet.android.istustudents.UserInformation
import com.chistoedet.android.istustudents.di.App
import com.chistoedet.android.istustudents.ui.main.studentOffice.StudentOfficeViewModel
import okhttp3.internal.notify

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private var callbacks: Callbacks? = null

    private val app = (application as App)

    // TODO mutable данные

    val passport = MutableLiveData<String>()
    val inn = MutableLiveData<String>()
    val snils = MutableLiveData<String>()

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