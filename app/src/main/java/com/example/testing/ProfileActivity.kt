package com.example.testing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.MenuItem

import android.widget.TextView

import androidx.appcompat.app.ActionBarDrawerToggle

import kotlinx.android.synthetic.main.activity_main.*

class ProfileActivity : AppCompatActivity() {

    lateinit var etEmail: TextView
    lateinit var etPassword: TextView
    lateinit var etUsername: TextView

    private val URL :String = "http://10.0.2.2/login/login.php"
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_screen)
        etEmail = findViewById(R.id.lblEmail)
        etUsername = findViewById(R.id.lblUsername)
        etPassword = findViewById(R.id.lblPassword)


        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav_view.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.nav_profile ->startActivity(Intent(this, ProfileActivity::class.java))
            }
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}