package com.chistoedet.android.istustudents.ui.main.studentOffice

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.chistoedet.android.istustudents.di.App
import com.chistoedet.android.istustudents.di.DaggerActivityComponent
import com.chistoedet.android.istustudents.di.SharedRepositoryImpl
import com.chistoedet.android.istustudents.statement.StatementFactory
import javax.inject.Inject

class StudentOfficeViewModel(application: Application) : AndroidViewModel(application) {

    private val app = (application as App)

    var component = DaggerActivityComponent.factory().create(application)
    // TODO mutable данные

    @Inject
    lateinit var shared : SharedRepositoryImpl
    init {

    }

    fun formStatement() {
        val user = app.getUser()
        if (user!=null) StatementFactory.formStatement(shared.getUserInformation(user) , user)
    }

}