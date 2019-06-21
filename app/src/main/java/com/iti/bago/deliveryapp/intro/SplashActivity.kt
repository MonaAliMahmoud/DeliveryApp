package com.iti.bago.deliveryapp.intro

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import androidx.core.app.ActivityCompat
import com.iti.bago.deliveryapp.MainActivity
import com.iti.bago.deliveryapp.R
import com.iti.bago.deliveryapp.SharedPref

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT:Long=2000 // 2 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                111
            )
        }

        var pref : SharedPref? =SharedPref()

        val isFirstRun = pref?.getIsLoggedIn(this)

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            if (isFirstRun!!) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else {
                val loginFragment = LoginFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.add(R.id.intro_frame, loginFragment)
                fragmentTransaction.commitAllowingStateLoss()
            }

        }, SPLASH_TIME_OUT)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
//        super.onSaveInstanceState(outState, outPersistentState)
    }
}
