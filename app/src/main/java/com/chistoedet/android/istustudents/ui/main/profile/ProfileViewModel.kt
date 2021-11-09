package com.chistoedet.android.istustudents.ui.main.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.chistoedet.android.istustudents.UserInformation
import com.chistoedet.android.istustudents.di.App
import com.chistoedet.android.istustudents.di.DaggerActivityComponent
import com.chistoedet.android.istustudents.di.SharedRepositoryImpl
import javax.inject.Inject

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val app = (application as App)

    var component = DaggerActivityComponent.factory().create(application)
    // TODO mutable данные

    @Inject
    lateinit var shared : SharedRepositoryImpl

    init {
            component.inject(this)
        }

    fun getInformation() : UserInformation? {
        val user = app.getUser()
        if (user != null) {
            return shared.getUserInformation(user)
        }
        else return null
    }

    fun saveInfo(saveInformation: UserInformation) {
        val user = app.getUser()
        if (user != null) {
            shared.saveUserInfo(user, saveInformation)
        }
    }

}