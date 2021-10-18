package com.chistoedet.android.istustudents.ui.main.studentOffice

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.chistoedet.android.istustudents.di.App
import com.chistoedet.android.istustudents.statement.StatementFactory

class StudentOfficeViewModel(application: Application) : AndroidViewModel(application) {

    private var callbacks: Callbacks? = null
    private val app = (application as App)

    interface Callbacks {
        fun onProfile(oldFragment: Fragment, fragmentManager: FragmentManager)
    }

    init {

    }

    fun formStatement() {
        StatementFactory.formStatement(app.getUserInformation(), app.getUser())
    }

}