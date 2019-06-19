package com.iti.bago.deliveryapp

import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.Fragment
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.iti.bago.deliveryapp.menu.current_order.CurrentOrderFragment
import com.iti.bago.deliveryapp.menu.notification.NotificationFragment
import com.iti.bago.deliveryapp.menu.SettingsFragment
import com.iti.bago.deliveryapp.tracking.TaskLoadedCallback

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener , TaskLoadedCallback {

    var currentFrame: Int = 0

    override fun onTaskDone(vararg values: Any) {
        Toast.makeText(this,
            "mona", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentFrame = R.id.backhome

        if (savedInstanceState == null) {
            val homeFragment = HomeFragment()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.content_frame, homeFragment)
            fragmentTransaction.commit()
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

//        val sharedPrefUtil = SharedPrefUtil()
//        val id = sharedPrefUtil.getId(this)
//        val token = sharedPrefUtil.getToken(this)
//        viewModel.setId_Token(id!!, token!!)

//        FirebaseInstanceId.getInstance().instanceId
//            .addOnCompleteListener(OnCompleteListener { task ->
//                if (!task.isSuccessful) {
//                    Log.w(ContentValues.TAG, "getInstanceId failed", task.exception)
//                    return@OnCompleteListener
//                }
//
//                // Get new Instance ID customer_token
//                val firebaseobj: FireBase_Obj? = null
//                val token = task.result?.token
//                Log.i("driver token: ", token)
//                // Log and toast
//                val service = ServiceBuilder.RetrofitManager.getInstance()?.create(RetrofitApi::class.java)
////                val call: Call<Void>? = service?.postToken(firebaseobj!!)
////                call?.enqueue(object : Callback<Void> {
////
////
////                })
//            }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        displaySelectedScreen(item.itemId)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun displaySelectedScreen( itemId : Int){

        var fragment : Fragment? = null

        when (itemId) {
            R.id.backhome -> {
                fragment = HomeFragment()
            }

            R.id.current_order -> {
                fragment = CurrentOrderFragment()
            }
            R.id.last_order -> {

            }
            R.id.notification -> {
                fragment = NotificationFragment()
            }
            R.id.settings -> {
                fragment = SettingsFragment()
            }
        }

        if (fragment != null){
            val frgMng = supportFragmentManager
            val frgTran = frgMng!!.beginTransaction()
            frgTran.replace(R.id.content_frame, fragment).addToBackStack(null).commit()
        }
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
