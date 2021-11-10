package com.chistoedet.android.istustudents.ui.main.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.chistoedet.android.core.remote.istu.ISTUProvider
import com.chistoedet.android.core.remote.istu.ISTUProviderImpl
import com.chistoedet.android.istustudents.UserInformation
import com.chistoedet.android.istustudents.di.App
import com.chistoedet.android.istustudents.di.SharedRepositoryImpl

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val app = (application as App)

  //  var component = DaggerActivityComponent.factory().create(application)
    // TODO mutable данные

    var api : ISTUProvider = ISTUProviderImpl()

    var shared: SharedRepositoryImpl = SharedRepositoryImpl(application)

    init {
        //    component.inject(this)
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