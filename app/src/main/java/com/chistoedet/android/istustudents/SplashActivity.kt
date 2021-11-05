package com.chistoedet.android.istustudents

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chistoedet.android.istustudents.databinding.SpashActivityBinding
import com.chistoedet.android.istustudents.ui.splash.login.LoginFragment

class SplashActivity : AppCompatActivity(), LoginFragment.Callbacks {

    private lateinit var binding: SpashActivityBinding

    private lateinit var viewModel : SplashActivityViewModel

    private lateinit var stateObserver : Observer<TokenState>

    private val POLL_WORK = "POLL_WORK"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SpashActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SplashActivityViewModel::class.java)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, LoginFragment.newInstance())
            .commit()

        stateObserver = Observer {

            Log.d("State", "onCreate: ${it::class.simpleName}")
            when(it) {
                is TokenState.CheckingState -> {
                    onCheckingState()//showForm()

                }
                is TokenState.IsRelevance -> {
                    onMain()
                    //chatViewModel.myChatLiveData.removeObserver(chatObserver)

                }
                is TokenState.IsNotRelevance -> {
                    Log.d("State", "onCreate: ис нот релеванс")
                    onLogin()

                    //chatViewModel.myChatLiveData.removeObserver(chatObserver)
                }
                is TokenState.ConnectionError -> {
                    showError()
                }


                else -> {

                }
            }
        }
    }

    private fun onCheckingState() {
        binding.progressBar.visibility = View.VISIBLE

        binding.connectionErrorText.visibility = View.INVISIBLE
        binding.container.visibility = View.INVISIBLE
    }


    override fun onStart() {
        super.onStart()
        viewModel.state.observe(this, stateObserver)
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkTokenRelevance()
    }

    override fun onStop() {
        super.onStop()
        viewModel.state.removeObserver(stateObserver)
    }

    private fun showError() {
        binding.connectionErrorText.visibility = View.VISIBLE
        binding.connectionErrorText.setOnClickListener {
            viewModel.checkTokenRelevance()
        }

        binding.progressBar.visibility = View.INVISIBLE
        binding.container.visibility = View.INVISIBLE
    }

    private fun onLogin() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.container.visibility = View.VISIBLE


    }

    private fun onMain() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onMainFromLogin(fragment: LoginFragment) {
        Log.d("Splash", "onMain")

        onMain()
    }



}