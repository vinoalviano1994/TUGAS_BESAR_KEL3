package com.alviano.ugd_kel3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class homeActivity : AppCompatActivity() {
    private val accountFragment = AccountFragment()
    private val homeFragment = HomeFragment()
    private val pesananFragment = PesananFragment()
    private val costumerFragment = Fragment_customer()

    lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        changeFragment(costumerFragment)
        bottomNav = findViewById(R.id.bottom_navigation) as BottomNavigationView
        bottomNav.setOnNavigationItemReselectedListener{
            when (it.itemId){
                R.id.menu_home -> {
                    changeFragment(homeFragment)
                    return@setOnNavigationItemReselectedListener
                }
                R.id.menu_Order -> {
                    changeFragment(costumerFragment)
                    return@setOnNavigationItemReselectedListener
                }
                R.id.menu_account -> {
                    val moveCamera = Intent(this@homeActivity, Camera::class.java)
                    startActivity(moveCamera)
                }
            }
        }


    }

    fun changeFragment(fragment: Fragment?){
        if(fragment != null){
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = MenuInflater(this)
        menuInflater.inflate(R.menu.bottom_navigation, menu)
        return true
    }



}