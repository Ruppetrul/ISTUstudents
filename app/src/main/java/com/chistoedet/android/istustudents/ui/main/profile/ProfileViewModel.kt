package com.chistoedet.android.istustudents.ui.main.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.chistoedet.android.istustudents.UserInformation
import com.chistoedet.android.istustudents.di.App

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val app = (application as App)

    // TODO mutable данные

    init {
        updateInfo()
        }

    fun updateInfo() : UserInformation {

        return getInformation()

    }

    private fun getInformation() : UserInformation {
        return app.getUserInformation()
    }

    fun saveInfo(saveInformation: UserInformation) {
        app.saveUserInfo(saveInformation)
        updateInfo()
    }

}