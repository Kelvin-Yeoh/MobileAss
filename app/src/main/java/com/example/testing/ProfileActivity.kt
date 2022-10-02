package com.example.testing

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.testing.databinding.FragmentDonationBinding
import com.example.testing.databinding.FragmentProfileFragmentBinding
import org.json.JSONArray

class ProfileActivity : Fragment() {

    private lateinit var binding : FragmentProfileFragmentBinding
    private val URL :String = "http://10.0.2.2/login/profile.php"
    private var username: TextView? = null
    private var useremail: TextView? = null
    private var password: TextView? = null
    private var txtTotalDonation: TextView? = null
    private var txtTotalMealDonated: TextView? = null
    private var btnLogout: Button? = null
    var PREFS_KEY = "prefs"
    var EMAIL_KEY = "email"
    var NAME_KEY = "name"
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_fragment, container, false)
        sharedPreferences =  binding.root.context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        btnLogout = binding.btnLogout

        display()
        btnLogout!!.setOnClickListener {
            clickLogout()
        }
        return binding.root
    }

    private fun display() {

        username = binding.lblUsernametesting
        useremail = binding.lblEmailtesting
        password = binding.lblPasswordtesting
        txtTotalDonation = binding.textViewTotalDonations
        txtTotalMealDonated = binding.textViewTotalMealDonated


        val email = sharedPreferences.getString(EMAIL_KEY, null)!!
        txtTotalDonation!!.text = email
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
                    txtTotalMealDonated!!.text = dataobj.getInt("donated_meal").toString();
                    val totalDonation = dataobj.getInt("donated_meal") * 3.5
                    txtTotalDonation!!.text = totalDonation.toString();
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

    private fun clickLogout() {
        sharedPreferences = binding.root.context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        removeData()
        val intent = Intent(binding.root.context, MainActivity::class.java)
        startActivity(intent)
    }

    fun removeData(){
        sharedPreferences = binding.root.context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)

        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
        editor?.clear()
        editor?.commit()
    }
}