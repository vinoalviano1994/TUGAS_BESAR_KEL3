package com.alviano.ugd_kel3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        setContentView(R.layout.activity_auth)
        Log.d("extras",intent?.extras.toString())
    }

    fun changeFragment(fragment: Fragment?) {
        if (fragment != null) {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit()
        }
    }
}