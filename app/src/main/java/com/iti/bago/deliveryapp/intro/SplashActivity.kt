package com.iti.bago.deliveryapp.intro

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import androidx.core.app.ActivityCompat
import com.iti.bago.deliveryapp.R

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

//        var pref : SharedPref? =null
//        pref=SharedPref()
//
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
//                startActivity(Intent(this, MainActivity::class.java))
//
                // close this activity
//                finish()

            val loginFragment = LoginFragment()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.intro_frame, loginFragment)
            fragmentTransaction.commitAllowingStateLoss()

//            startActivity(Intent(this, LoginActivity::class.java))

        }, SPLASH_TIME_OUT)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
//        super.onSaveInstanceState(outState, outPersistentState)
    }
}
