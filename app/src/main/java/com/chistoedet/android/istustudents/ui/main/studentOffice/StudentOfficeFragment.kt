package com.chistoedet.android.istustudents.ui.main.studentOffice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.chistoedet.android.istustudents.R
import com.chistoedet.android.istustudents.databinding.FragmentStudentBinding
import com.chistoedet.android.istustudents.di.App
import com.chistoedet.android.istustudents.ui.splash.login.LoginFragment
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope

private val TAG = StudentOfficeFragment::class.simpleName
class StudentOfficeFragment : Fragment() {

    private lateinit var viewModel: StudentOfficeViewModel
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
        viewModel =
            ViewModelProvider(this).get(StudentOfficeViewModel::class.java)

        _binding = FragmentStudentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.personalData.setOnClickListener {

            findNavController().navigate(R.id.action_nav_student_to_nav_profile)

        }

        binding.formStatement.setOnClickListener {
            viewModel.formStatement()
        }

       /* binding.vkLogin.setOnClickListener {
            activity?.let { it1 -> VK.login(it1, arrayListOf(VKScope.WALL, VKScope.FRIENDS)) }
        }*/

        return root
    }

    override fun onResume() {
        super.onResume()
        var user = app.getUser()
        Log.d(TAG, "onResume: ${user?.getFio()}")
        binding.user = user

        updateVKButton()
    }

    private fun updateVKButton(){
        binding.vkLogin.apply {
            VK.isLoggedIn().let {
                if (it) {
                    this.text = "Выйти из ВК"
                    this.setOnClickListener {
                        VK.logout().let {
                            updateVKButton()
                        }
                    }
                } else {
                    this.text = "Войти в ВК"
                    this.setOnClickListener {
                         activity?.let { it1 -> VK.login(it1, arrayListOf(VKScope.WALL, VKScope.FRIENDS))
                    }
                }
            //this.isEnabled = !it
            }
        }
    }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}