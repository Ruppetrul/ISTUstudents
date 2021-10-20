package com.chistoedet.android.istustudents

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.chistoedet.android.istustudents.ui.splash.login.LoginFragment
import com.chistoedet.android.istustudents.ui.splash.login.LoginViewModel

class SplashActivity : AppCompatActivity(), LoginViewModel.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.spash_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
        }

    }

    override fun onMain() {
        Log.d("Splash", "onMain")
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}