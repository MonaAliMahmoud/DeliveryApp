package com.iti.bago.deliveryapp

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT:Long=5000 // 2 sec

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

//        var pref : PrefManager? =null
//        pref=PrefManager(this)

//        val isFirstRun =pref?.getIsFirst()


//
//        val isFirstRun = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
//            .getBoolean("isFirstRun", true)

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

//            if (isFirstRun!!) {
//                //show start activity
//
//                getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
//                    .putBoolean("isFirstRun", false).apply()
//                pref.setFirst(false)
//                startActivity(Intent(this, MainActivity::class.java))
////                Toast.makeText(this, "First Run", Toast.LENGTH_LONG)
////                    .show()
//            }else{
                startActivity(Intent(this, MainActivity::class.java))

                // close this activity
                finish()
        }, SPLASH_TIME_OUT)
    }
}
