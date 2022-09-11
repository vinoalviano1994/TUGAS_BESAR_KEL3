package com.alviano.ugd_kel3

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        changeFragment(costumerFragment)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_home){
            changeFragment(homeFragment)
        }else if(item.itemId == R.id.menu_Order){
            changeFragment(costumerFragment)
        }else if(item.itemId == R.id.menu_account){

        }else if(item.itemId == R.id.menu_notifikasi){

        }
        return super.onOptionsItemSelected(item)
    }

}