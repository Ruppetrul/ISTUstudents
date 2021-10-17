package com.chistoedet.android.istustudents.ui.main.studentOffice

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel

class StudentOfficeViewModel : ViewModel() {

    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onProfile(oldFragment: Fragment, fragmentManager: FragmentManager)
    }

    init {

    }

    fun toProfile(fragment: Fragment, fragmentManager: FragmentManager) {
        callbacks?.onProfile(fragment, fragmentManager)
    }
}