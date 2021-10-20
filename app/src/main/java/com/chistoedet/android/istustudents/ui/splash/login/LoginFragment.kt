package com.chistoedet.android.istustudents.ui.splash.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chistoedet.android.istustudents.databinding.LoginFragmentBinding

private val TAG = LoginFragment::class.java.simpleName
class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    private lateinit var stateObserver : Observer<LoginState>

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

        stateObserver = Observer {
            Log.i(TAG, "new state")
            when(it) {
                is LoginState.InputState -> {
                    showForm()
                    Log.i(TAG, "LoadingState")
                }
                is LoginState.ErrorState -> {
                    showError()
                    //chatViewModel.myChatLiveData.removeObserver(chatObserver)
                    Log.i(TAG, "ErrorState")
                }
                is LoginState.LoggingState -> {
                    showLogging()
                    Log.i(TAG, "NoMessageState")
                }
                is LoginState.LoginErrorState -> {
                    showLoginError(it.error)
                }
                else -> {

                }
            }
        }

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            Log.d(TAG, "onViewCreated: click")

            // TODO проверки

            viewModel.onLogin(
                binding.loginEt.text.toString(),
                binding.passwordEt.text.toString())

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.state.observe(viewLifecycleOwner, stateObserver)
    }

    override fun onStop() {
        super.onStop()
        viewModel.state.removeObserver(stateObserver)
    }

    private fun showError() {
        Log.i(TAG,"showError")
        binding.loginEt.visibility = View.INVISIBLE
        binding.passwordEt.visibility = View.INVISIBLE
        binding.button.visibility = View.INVISIBLE
        binding.welcomeText.visibility = View.INVISIBLE

        binding.errorText.visibility = View.VISIBLE
    }

    private fun showLogging() {
        Log.i(TAG,"showChat")
  /*      binding.loginEt.visibility = View.INVISIBLE
        binding.passwordEt.visibility = View.INVISIBLE
        binding.button.visibility = View.INVISIBLE
        binding.welcomeText.visibility = View.INVISIBLE

        binding.errorText.visibility = View.VISIBLE*/
    }

    private fun showLoginError(errorText: String) {
        binding.loginEt.visibility = View.VISIBLE
        binding.passwordEt.visibility = View.VISIBLE
        binding.button.visibility = View.VISIBLE
        binding.welcomeText.visibility = View.VISIBLE

        Toast.makeText(context, errorText, Toast.LENGTH_LONG).show()

    }

    private fun showForm() {
        Log.i(TAG,"showForm")
        binding.loginEt.visibility = View.VISIBLE
        binding.passwordEt.visibility = View.VISIBLE
        binding.button.visibility = View.VISIBLE
        binding.welcomeText.visibility = View.VISIBLE

        binding.errorText.visibility = View.INVISIBLE
        /*  binding.loadingText.visibility = View.VISIBLE
          binding.errorText.visibility = View.INVISIBLE
          binding.chatText.visibility = View.INVISIBLE*/
        /*message.visibility = View.INVISIBLE
        messages.visibility = View.INVISIBLE
        errorImage.visibility = View.INVISIBLE
        errorText.visibility = View.INVISIBLE
        tryAgainButton.visibility = View.INVISIBLE
        loadingImage.visibility = View.VISIBLE*/
    }
}