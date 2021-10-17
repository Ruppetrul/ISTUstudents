package com.chistoedet.android.istustudents.ui.main.studentOffice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.chistoedet.android.istustudents.R
import com.chistoedet.android.istustudents.databinding.FragmentStudentBinding
import com.chistoedet.android.istustudents.ui.main.profile.ProfileFragment
import com.chistoedet.android.istustudents.ui.splash.login.LoginFragment

private val TAG = StudentOfficeFragment::class.simpleName
class StudentOfficeFragment : Fragment() {

    private lateinit var studentOfficeViewModel: StudentOfficeViewModel
    private var _binding: FragmentStudentBinding?= null

    private val binding get() = _binding!!

    companion object {
        fun newInstance() = LoginFragment()
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}