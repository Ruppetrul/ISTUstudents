package com.chistoedet.android.istustudents



import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
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
import com.chistoedet.android.istustudents.network.response.chats.Staffs
import com.chistoedet.android.istustudents.network.response.user.UserResponse
import com.chistoedet.android.istustudents.ui.main.messenger.list.ContactListAdapter
import com.google.android.material.navigation.NavigationView

private val TAG = MainActivity::class.simpleName
class MainActivity : AppCompatActivity(), ContactListAdapter.Callbacks, NavigationView.OnNavigationItemSelectedListener  {

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



  /*  private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish()
        }

        val count = supportFragmentManager.backStackEntryCount
        Log.d(TAG, "onBackPressed: $count")

        if (count == 0) {
            super.onBackPressed()
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

            Handler().postDelayed(Runnable {
                doubleBackToExitPressedOnce = false }, 2000)


        } else {
            supportFragmentManager.popBackStack()
        }

    }*/

    override fun onChatSelected(user: Staffs) {
        // navController.navigate(R.id.action_nav_contact_list_to_nav_contact_chat)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onNavigationItemSelected: ${item}")
       /* when (item.itemId) {
                R.id.nav_contact_list ->
                supportFragmentManager.beginTransaction().replace(
                R.id.nav_host_fragment_content_main,
                ContactListFragment.newInstance())
                .addToBackStack(null)
                .commit()


           *//* R.id.nav_profile -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                ProfileFragment()
            ).commit()
            R.id.nav_share -> Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
            R.id.nav_send -> Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show()
        *//*}


        drawerLayout?.closeDrawer(GravityCompat.START);*/

        return true
    }


}


