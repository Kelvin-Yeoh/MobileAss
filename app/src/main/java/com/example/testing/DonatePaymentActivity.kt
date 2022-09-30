package com.example.testing

import android.os.Bundle
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
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.testing.databinding.FragmentDonatePaymentActivityBinding
import com.example.testing.databinding.FragmentDonationBinding
import com.squareup.picasso.Picasso


class DonatePaymentActivity : Fragment() {

    private val URLstring = "http://10.0.2.2/updateEvent.php"
    private lateinit var binding : FragmentDonatePaymentActivityBinding
    private var textTitle: TextView? = null
    private var textDetail: TextView? = null
    private var textTotal: TextView? = null
    private var btnConfirm: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_donate_payment_activity, container, false)
        val args = this.arguments

        textTitle = binding.dpTextViewTitle
        textDetail = binding.dpTextViewTotal
        textTotal = binding.dpTextViewMYR
        btnConfirm = binding.btnConfirm

        textTitle!!.text = args?.getString("textTitle")
        textDetail!!.text = args?.getInt("currentMeal").toString() + " | " + args?.getString("totalMYR")
        textTotal!!.text = args?.getString("totalMYR")

        btnConfirm!!.setOnClickListener {
            updateToDatabase()
        }

        return binding.root
    }

    private fun updateToDatabase() {
        val args = this.arguments
        val id = args?.getInt("id")!!.toString().trim{ it <= ' ' }
        val currentMealDB = args?.getInt("currentMeal").toString().trim{ it <= ' ' }

        val request: StringRequest = object : StringRequest(
            Method.POST, URLstring,
            Response.Listener { response ->
                Toast.makeText(binding.root.context, response, Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    binding.root.context,
                    error.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val map: MutableMap<String, String> = HashMap()
                map["id"] = id
                map["current_meal_quantity"] = currentMealDB
                return map
            }
        }
        val queue = Volley.newRequestQueue(binding.root.context)
        queue.add(request)
    }

    companion object {
        private const val url = "http://10.0.2.2/eventcreate.php"
    }

}