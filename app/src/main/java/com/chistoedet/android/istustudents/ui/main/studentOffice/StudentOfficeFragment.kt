package com.chistoedet.android.istustudents.ui.main.studentOffice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.chistoedet.android.core.remote.istu.ISTUProviderImpl
import com.chistoedet.android.istustudents.Config
import com.chistoedet.android.istustudents.R
import com.chistoedet.android.istustudents.databinding.FragmentStudentBinding
import com.chistoedet.android.istustudents.di.App
import com.chistoedet.android.istustudents.di.SharedRepositoryImpl
import com.chistoedet.android.istustudents.services.news.NewsPollingWorker
import com.chistoedet.android.istustudents.ui.splash.login.LoginFragment
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.auth.VKScope
import com.vk.dto.common.id.UserId
import com.vk.sdk.api.wall.WallService
import com.vk.sdk.api.wall.dto.WallGetResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private val TAG = StudentOfficeFragment::class.simpleName
class StudentOfficeFragment : Fragment() {

    private lateinit var viewModel: StudentOfficeViewModel
    private var _binding: FragmentStudentBinding?= null

    private val binding get() = _binding!!
    private lateinit var app : App
    var api : ISTUProviderImpl = ISTUProviderImpl()
    lateinit var sharedRepository : SharedRepositoryImpl

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        app = (activity?.application as App)
        sharedRepository = SharedRepositoryImpl(requireContext())
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

        val user = app.getUser()
        val id = user!!.getId()
        binding.vkStudentTest.setOnClickListener {
            GlobalScope.launch{
                Log.d(TAG, "Вызываю student с student_id $id")
                Log.d(TAG, "Вызываю student с token ${app.getToken()}")
                var a = app.getToken()?.let { it1 ->
                    if (id != null) {
                        api.fetchStudent(it1, id)
                    }
                }
            }
        }

        binding.vkSessionTest.setOnClickListener {
            GlobalScope.launch{
                Log.d(TAG, "Вызываю session с student_id ${user?.getId()}")
                var b = app.getToken()?.let { it1 -> api.fetchSession(it1, user!!.getId()!!+1) }
            }
        }

        binding.vkNewsTest.setOnClickListener {
            if (VK.isLoggedIn()) {
                VK.execute(
                    WallService().wallGet(
                        UserId(Config.VK_PUBLIC_ID),
                        null,
                        null,
                        5,
                        null,
                        null), object: VKApiCallback<WallGetResponse> {
                        override fun success(result: WallGetResponse) {

                            NewsPollingWorker.showResult(result, sharedRepository, requireContext())


                        }

                        override fun fail(error: Exception) {
                            TODO("Not yet implemented")
                        }
                    }
                )
            }
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