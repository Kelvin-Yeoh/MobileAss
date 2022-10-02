package com.example.testing

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.MenuItem
import android.widget.Button

import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

import kotlinx.android.synthetic.main.activity_main.*

class UserMainActivity : AppCompatActivity() {

    val URL :String = "http://10.0.2.2/login/login.php"
    lateinit var toggle: ActionBarDrawerToggle
    val URLstring = "http://10.0.2.2/getevent.php"
    private lateinit var rvAdapter : RvAdapter
    private var recyclerView: RecyclerView? = null
    val data = ArrayList<ItemsViewModel>()
    private var testing: TextView? = null
    private var btnViewMore: Button? = null
    private var toolbar: Toolbar? = null
    var PREFS_KEY = "prefs"
    var EMAIL_KEY = "email"
    var NAME_KEY = "name"
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences =  this.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        replaceFragment(EventViewActivity(), title.toString())

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav_view.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.nav_profile -> replaceFragment(ProfileActivity(), it.title.toString())
                R.id.nav_home -> replaceFragment(EventViewActivity(), it.title.toString())
                R.id.nav_logout -> clickLogout()
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment, title : String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.framelayout,fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun clickLogout() {
        sharedPreferences = this.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        removeData()
        val intent = Intent(this@UserMainActivity, MainActivity::class.java)
        startActivity(intent)
    }

    fun removeData(){
        sharedPreferences = this.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)

        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
        editor?.clear()
        editor?.commit()
    }
}