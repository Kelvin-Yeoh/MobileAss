package com.example.testing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class ProfileActivity : AppCompatActivity() {

    lateinit var etEmail: TextView
    lateinit var etPassword: TextView
    lateinit var etUsername: TextView

    private val URL :String = "http://10.0.2.2/login/login.php"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_screen)
        etEmail = findViewById(R.id.lblEmail)
        etUsername = findViewById(R.id.lblUsername)
        etPassword = findViewById(R.id.lblPassword)



    }


}