package com.example.testing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.MenuItem

import android.widget.TextView

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var etEmail: TextView
    lateinit var etPassword: TextView
    lateinit var etUsername: TextView

    private val URL :String = "http://10.0.2.2/login/login.php"
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav_view.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.nav_profile -> replaceFragment(profile_fragment(), it.title.toString())
                R.id.nav_home -> replaceFragment(EventViewActivity(), it.title.toString())
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment, title : String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.framelayout,fragment)
        fragmentTransaction.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}