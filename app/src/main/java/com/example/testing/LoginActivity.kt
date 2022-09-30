package com.example.testing

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.testing.databinding.FragmentLoginActivityBinding


class LoginActivity : Fragment() {

    private var email: String? = null
    private var password: String? = null
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    private var btnLogin: Button? = null

    private val URL :String = "http://10.0.2.2/login/login.php"
    private lateinit var binding : FragmentLoginActivityBinding

    lateinit var sharedPreferences: SharedPreferences
    var PREFS_KEY = "prefs"
    var EMAIL_KEY = "email"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_activity, container, false)

        etEmail = binding.txtEmail
        etPassword = binding.txtPassword
        btnLogin = binding.btnLogin


        sharedPreferences = binding.root.context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        email = sharedPreferences.getString(EMAIL_KEY, "").toString()

        btnLogin!!.setOnClickListener {
            login()
        }
        return binding.root
    }

    private fun login() {
        var email = etEmail.getText().toString().trim()
        var password = etPassword.getText().toString().trim()
        if (email != "" && password != "") {
            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    Log.d("res", response)
                    if (response == "success") {
                        val editor: SharedPreferences.Editor=sharedPreferences.edit()
                        editor.putString(EMAIL_KEY, etEmail.text.toString())
                        editor.apply()
//                        val intent = Intent(binding.root.context, ProfileActivity::class.java)
//                        startActivity(intent)
                        val fragment = ProfileActivity()
                        fragmentManager?.beginTransaction()?.replace(R.id.framelayout,fragment)?.commit()
                    } else if (response == "failure") {
                        Toast.makeText(
                            binding.root.context,
                            "Invalid Login Id/Password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                },
                Response.ErrorListener { error ->
                    Toast.makeText(
                        binding.root.context,
                        error.toString().trim { it <= ' ' },
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    val data: MutableMap<String, String> = HashMap()
                    data["email"] = email
                    data["password"] = password
                    return data
                }
            }
            val requestQueue = Volley.newRequestQueue(binding.root.context)
            requestQueue.add(stringRequest)
        } else {
            Toast.makeText(binding.root.context, "Fields can not be empty!", Toast.LENGTH_SHORT).show()
        }
    }

    fun register(view: View?) {
        val intent = Intent(binding.root.context, Register::class.java)
        startActivity(intent)
        activity?.finish()
    }

}