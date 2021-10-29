package com.chistoedet.android.istustudents



import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.chistoedet.android.istustudents.databinding.ActivityMainBinding
import com.chistoedet.android.istustudents.network.response.user.UserResponse
import com.google.android.material.navigation.NavigationView
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.exceptions.VKAuthException
import com.vk.sdk.api.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch





private val TAG = MainActivity::class.simpleName
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel : MainActivityViewModel

    private lateinit var userDateObserver : Observer<UserResponse>

    private lateinit var navController : NavController

    var graph: NavGraph? = null

    var drawerLayout: DrawerLayout ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        /*binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

        drawerLayout = binding.drawerLayout

        val navView: NavigationView = binding.navView
        navView.setNavigationItemSelectedListener(this)

        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val navInflater = navController.navInflater
        graph = navInflater.inflate(R.navigation.mobile_navigation)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_student,
                R.id.nav_portfolio,
                R.id.nav_messenger,
                R.id.nav_news
            ), drawerLayout
        )

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.btnSingOut.setOnClickListener {
            viewModel.logout().let {
                finish()
            }
        }

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        userDateObserver = Observer<UserResponse> {
            val navHeader = navView.getHeaderView(0)
            val textName: TextView = navHeader.findViewById(R.id.drawer_header_name)
            val textInfo: TextView = navHeader.findViewById(R.id.drawer_header_info)

            textName.text = viewModel.userLiveData.value?.getFio()
            textInfo.text = viewModel.userLiveData.value?.getStudent()?.get(0)?.group
        }

        viewModel.userLiveData.observe(this, userDateObserver)
        //viewModel.updateUser()

        CoroutineScope(Dispatchers.Main).launch {
            VK.isLoggedIn().let {
                if (it) {

                } else {
                    showAlertDialog()
                }
            }
        }

        //VK.initialize(this)

    }

    fun showAlertDialog() {
        val webaddress = SpannableString(
            "Для полноценной работы приложения необходимо авторизоваться в ВКонтакте. Иначе будут недоступны некоторые функции"
        )
        //Linkify.addLinks(webaddress, Linkify.ALL)

        val aboutDialog: AlertDialog = AlertDialog.Builder(
            this
        ).setMessage(webaddress)
            .setPositiveButton("Войти", DialogInterface.OnClickListener { dialog, which ->
                VK.login(this@MainActivity, arrayListOf(VKScope.WALL, VKScope.FRIENDS))
            })
            .setNegativeButton("Я козёл", DialogInterface.OnClickListener { dialog, which ->

            }).create()

        aboutDialog.show()

        /*(aboutDialog.findViewById(android.R.id.message) as TextView).movementMethod =
            LinkMovementMethod.getInstance()*/
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                Log.d("TAG", "onLogin: ")
            }

            override fun onLoginFailed(authException: VKAuthException) {
                Log.d("TAG", "onLoginFailed: ")
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private val tokenTracker = object: VKTokenExpiredHandler {
        override fun onTokenExpired() {
            Log.d("TOKEN", "onTokenExpired")
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

}


