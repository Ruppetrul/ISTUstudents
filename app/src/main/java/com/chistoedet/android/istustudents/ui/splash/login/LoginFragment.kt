package com.chistoedet.android.istustudents.ui.splash.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.chistoedet.android.istustudents.databinding.LoginFragmentBinding

private val TAG = LoginFragment::class.java.simpleName
class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    private var _binding: LoginFragmentBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = LoginFragmentBinding.inflate(layoutInflater, container, false)
        val root = binding.root
        return root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        context?.let { viewModel.test(it) }
        binding.loginEt.setText(viewModel.getLastLogin())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            Log.d(TAG, "onViewCreated: click")

            // TODO проверки

            viewModel.onLogin(binding.loginEt.text.toString(),
                binding.passwordEt.text.toString())

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}