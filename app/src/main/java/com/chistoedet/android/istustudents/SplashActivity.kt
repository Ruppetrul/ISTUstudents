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
import com.chistoedet.android.istustudents.ui.splash.login.LoginViewModel

class SplashActivity : AppCompatActivity(), LoginViewModel.Callbacks {

    private lateinit var binding: SpashActivityBinding

    private lateinit var viewModel : SplashActivityViewModel

    private lateinit var stateObserver : Observer<TokenState>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SpashActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SplashActivityViewModel::class.java)

        stateObserver = Observer {

            Log.d("State", "onCreate: ${it::class.simpleName}")
            when(it) {
                is TokenState.CheckingState -> {
                    //showForm()

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


                else -> {

                }
            }
        }
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

    private fun onLogin() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.container.visibility = View.VISIBLE

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, LoginFragment.newInstance())
            .commitNow()
    }

    override fun onMain() {
        Log.d("Splash", "onMain")
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}