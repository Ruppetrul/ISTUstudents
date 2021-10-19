package com.chistoedet.android.istustudents.ui.main.studentOffice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.chistoedet.android.istustudents.R
import com.chistoedet.android.istustudents.databinding.FragmentStudentBinding
import com.chistoedet.android.istustudents.di.App
import com.chistoedet.android.istustudents.statement.StatementFactory
import com.chistoedet.android.istustudents.statement.StatementFactory.formStatement
import com.chistoedet.android.istustudents.ui.main.profile.ProfileFragment
import com.chistoedet.android.istustudents.ui.splash.login.LoginFragment
import java.sql.Statement

private val TAG = StudentOfficeFragment::class.simpleName
class StudentOfficeFragment : Fragment() {

    private lateinit var studentOfficeViewModel: StudentOfficeViewModel
    private var _binding: FragmentStudentBinding?= null

    private val binding get() = _binding!!
    private lateinit var app : App

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        app = (activity?.application as App)
        studentOfficeViewModel =
            ViewModelProvider(this).get(StudentOfficeViewModel::class.java)

        _binding = FragmentStudentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //context?.let { studentOfficeViewModel.variableCallbacks(it) }

        binding.personalData.setOnClickListener {
           // studentOfficeViewModel.toProfile(this, parentFragmentManager)

            //studentOfficeViewModel.toProfile(this, parentFragmentManager)
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, ProfileFragment.newInstance())
                //.detach(oldFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.formStatement.setOnClickListener {
            studentOfficeViewModel.formStatement()
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        var user = app.getUser()
        Log.d(TAG, "onResume: ${user?.getFio()}")
        binding.user = user
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}