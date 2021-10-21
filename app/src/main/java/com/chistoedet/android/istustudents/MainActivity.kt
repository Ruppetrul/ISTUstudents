package com.chistoedet.android.istustudents

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.chistoedet.android.istustudents.databinding.ActivityMainBinding
import com.chistoedet.android.istustudents.network.response.user.UserResponse
import com.google.android.material.navigation.NavigationView




private val TAG = MainActivity::class.simpleName
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel : MainActivityViewModel

    private lateinit var userDateObserver : Observer<UserResponse>

    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        /*binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

        val drawerLayout: DrawerLayout = binding.drawerLayout

        val navView: NavigationView = binding.navView
        //val navController = findNavController(R.id.nav_host_fragment_content_main)
        navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_student, R.id.nav_portfolio, R.id.nav_messenger, R.id.nav_profile
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.btnSingOut.setOnClickListener {
            viewModel.logout().let {
                finish()
            }
        }

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        userDateObserver =  Observer<UserResponse> {
            val navHeader = navView.getHeaderView(0)
            val textName : TextView = navHeader.findViewById(R.id.drawer_header_name)
            val textInfo : TextView = navHeader.findViewById(R.id.drawer_header_info)

            textName.text = viewModel.userLiveData.value?.getFio()
            textInfo.text = viewModel.userLiveData.value?.getStudent()?.get(0)?.group
        }

        viewModel.userLiveData.observe(this, userDateObserver)
        //viewModel.updateUser()


    }

    override fun onStop() {
        super.onStop()
        viewModel.userLiveData.removeObserver(userDateObserver)
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_activity_tools, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


   /* override fun onProfile(oldFragment: Fragment, fragmentManager: FragmentManager) {

        val fragment = ProfileFragment.newInstance()
        fragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_content_main, fragment, oldFragment.tag)
            //.detach(oldFragment)
            .addToBackStack(oldFragment.tag)
            .commit()

        Log.d(TAG, "backStackEntryCount: ${fragmentManager.backStackEntryCount} ")

    }*/


    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish()
        }
        val count = supportFragmentManager.backStackEntryCount
        Log.d(TAG, "onBackPressed: $count")
        if (count == 0) {
            //super.onBackPressed()
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

            Handler().postDelayed(Runnable {
                doubleBackToExitPressedOnce = false }, 2000)


        } else {
            supportFragmentManager.popBackStack()
        }



    }


}


