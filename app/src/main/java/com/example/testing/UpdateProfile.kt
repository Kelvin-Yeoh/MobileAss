package com.example.testing

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.testing.databinding.FragmentUpdateProfileBinding
import org.json.JSONArray

class UpdateProfile : Fragment() {

    private var email: String? = null
    lateinit var UpdatetxtUsername: TextView
    lateinit var UpdatetxtEmail: TextView
    lateinit var UpdatetxtPassword: TextView
    lateinit var sharedPreferences: SharedPreferences
    var EMAIL_KEY = "email"
    var PREFS_KEY = "prefs"
    lateinit var btnUpdate: Button

    private lateinit var binding: FragmentUpdateProfileBinding

    private val URLUPDATE: String ="http://10.0.2.2/login/update.php"
    private val URLPROFILE: String ="http://10.0.2.2/login/profile.php"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update_profile, container, false)

        UpdatetxtUsername = binding.txtUsername
        UpdatetxtEmail = binding.tvEmail
        UpdatetxtPassword = binding.txtPassword
        btnUpdate = binding.btnUpdate

        sharedPreferences = binding.root.context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        email = sharedPreferences.getString(EMAIL_KEY, "").toString()
        UpdatetxtEmail!!.text = email


        display()

        btnUpdate.setOnClickListener {
            val name = UpdatetxtUsername.text.toString()
            val email = UpdatetxtEmail.text.toString()
            val password = UpdatetxtPassword.text.toString()
            updateUser(name, email, password)
        }

        return binding.root
    }

    private fun updateUser(name: String, email: String, password: String) {

        val requestQueue = Volley.newRequestQueue(binding.root.context)
        val stringRequest = object : StringRequest(
            Request.Method.POST,URLUPDATE,
            Response.Listener { response ->
                Toast.makeText(binding.root.context,
                    response,
                    Toast.LENGTH_SHORT).show()

                Log.d("res",response)
                val fragment = ProfileActivity()
                fragmentManager?.beginTransaction()?.replace(R.id.framelayout,fragment)?.commit()
            },Response.ErrorListener { error ->

            }){
            override fun getParams():HashMap<String,String>{
                val map = HashMap<String,String>()
                map["name"] = name
                map["email"] = email
                map["password"] = password
                return map
            }
        }
        requestQueue.add(stringRequest)
    }

    private fun display() {

        UpdatetxtUsername = binding.txtUsername
        UpdatetxtEmail = binding.tvEmail
        UpdatetxtPassword = binding.txtPassword
        btnUpdate = binding.btnUpdate


        val email = sharedPreferences.getString(EMAIL_KEY, null)!!
        val StringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URLPROFILE,
            Response.Listener{ response ->
                val userDetail = JSONArray(response)
                for (i in 0 until userDetail.length()) {
                    val dataobj = userDetail.getJSONObject(i)

                    //adding the product to product list

                    UpdatetxtUsername!!.text = dataobj.get("name").toString();

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
                data["email"] = email!!
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(binding.root.context)
        requestQueue.add(StringRequest)
    }


}