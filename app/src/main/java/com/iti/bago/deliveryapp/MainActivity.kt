package com.iti.bago.deliveryapp

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.iti.bago.deliveryapp.menu.current_order.CurrentOrderFragment
import com.iti.bago.deliveryapp.menu.notification.NotificationFragment
import com.iti.bago.deliveryapp.menu.SettingsFragment
import com.iti.bago.deliveryapp.tracking.TaskLoadedCallback

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener , TaskLoadedCallback {

    override fun onTaskDone(vararg values: Any) {
        Toast.makeText(this,
            "mona", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
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
