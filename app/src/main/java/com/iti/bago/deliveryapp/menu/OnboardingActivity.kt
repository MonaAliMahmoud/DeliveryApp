package com.iti.bago.deliveryapp.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.viewpager.widget.ViewPager
import com.iti.bago.deliveryapp.R

class OnboardingActivity : AppCompatActivity() {

    private lateinit var mPager: ViewPager
    lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
    }
}
