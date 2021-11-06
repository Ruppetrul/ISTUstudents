package com.chistoedet.android.istustudents.ui.main.studentOffice

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.chistoedet.android.istustudents.di.App
import com.chistoedet.android.istustudents.statement.StatementFactory

class StudentOfficeViewModel(application: Application) : AndroidViewModel(application) {

    private val app = (application as App)

    init {

    }

    fun formStatement() {
        StatementFactory.formStatement(app.getUserInformation(), app.getUser())
    }

}