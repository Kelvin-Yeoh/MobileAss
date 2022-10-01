package com.example.testing

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
import com.example.testing.databinding.FragmentLoginActivityBinding
import com.example.testing.databinding.FragmentRegisterActivityBinding


class RegisterActivity : Fragment() {
    lateinit var etName: EditText
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    lateinit var etReenterPassword: EditText
    lateinit var tvStatus: TextView
    lateinit var btnRegister: Button
    lateinit var txtLogin: TextView
    var name: String? = ""
    var email: String? = ""
    var password: String? = ""
    var reenterPassword: String? =""

    private val URL: String ="http://10.0.2.2/login/register.php"
    private lateinit var binding : FragmentRegisterActivityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_activity, container, false)

        etName = binding.txtUsername
        etEmail = binding.txtEmail
        etPassword = binding.txtPassword
        etReenterPassword = binding.txtReenterPassword
        tvStatus = binding.tvStatus
        btnRegister = binding.btnRegister
        txtLogin = binding.txtLoginScreen


        txtLogin.setOnClickListener {
            val fragment = LoginActivity()
            fragmentManager?.beginTransaction()?.replace(R.id.framelayout,fragment)?.commit()
        }

        return binding.root
    }

    fun save(view: View?) {
        name = etName.text.toString().trim { it <= ' ' }
        email = etEmail.text.toString().trim { it <= ' ' }
        password = etPassword.text.toString().trim { it <= ' ' }
        reenterPassword = etReenterPassword.text.toString().trim { it <= ' ' }
        if (password != reenterPassword) {
            Toast.makeText(binding.root.context, "Password Mismatch", Toast.LENGTH_SHORT).show()
        } else if (name != "" && email != "" && password != "") {
            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    tvStatus.text = response.toString()
                    Log.d("Register",response)
                    if (response == "success") {
                        tvStatus.text = "Successfully registered."
                        btnRegister.isClickable = false
                    } else if (response == "failure") {
                        tvStatus.text = "Something went wrong!"
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
                    data["name"] = name!!
                    data["email"] = email!!
                    data["password"] = password!!
                    return data
                }
            }
            val requestQueue = Volley.newRequestQueue(binding.root.context)
            requestQueue.add(stringRequest)
        }
    }

}