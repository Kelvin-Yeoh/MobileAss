package com.example.testing

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.MenuItem

import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.ActionBarDrawerToggle
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray

class ProfileActivity : AppCompatActivity() {



    private val URL :String = "http://10.0.2.2/login/profile.php"


    private var username: TextView? = null
    private var useremail: TextView? = null
    private var password: TextView? = null
    var PREFS_KEY = "prefs"
    var EMAIL_KEY = "email"
    var NAME_KEY = "name"
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_screen)



        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)

        display()



    }

    private fun display() {

        username = findViewById(R.id.lblUsername)
        useremail = findViewById(R.id.lblEmail)
        password = findViewById(R.id.lblPassword)



        val email = sharedPreferences.getString(EMAIL_KEY, null)!!
        val StringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URL,
            Response.Listener{ response ->
                val userDetail = JSONArray(response)
                for (i in 0 until userDetail.length()) {
                    val dataobj = userDetail.getJSONObject(i)

                    //adding the product to product list

                    username!!.text = dataobj.get("name").toString();
                    useremail!!.text = dataobj.get("email").toString();
                    password!!.text = dataobj.getString("password");
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    this@ProfileActivity,
                    error.toString().trim { it <= ' ' },
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val data: MutableMap<String, String> = HashMap()
                data["email"] = email!!
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(applicationContext)
        requestQueue.add(StringRequest)
    }




}