package com.example.testing

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.testing.databinding.FragmentAdminModifyActivityBinding
import com.example.testing.databinding.FragmentProfileFragmentBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_one.view.*
import org.json.JSONArray


class AdminModifyActivity : Fragment() {

    val URLstring = "http://10.0.2.2/getEventSingle.php"
    val url = "http://10.0.2.2/updateWholeEvent.php"
    private lateinit var binding : FragmentAdminModifyActivityBinding
    var editTextEventDescription: TextView? = null
    var editTextMeals: TextView? = null
    var editTextEventTitle: TextView? = null
    var image: ImageView? = null
    private var btnModifyEvent: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_modify_activity, container, false)

        editTextEventDescription = binding.editTextEventDescription
        editTextMeals = binding.editTextMeals
        editTextEventTitle = binding.editTextEventTitle
        image = binding.img

        getEvent()

        btnModifyEvent = binding.btnModifyEvent

        btnModifyEvent!!.setOnClickListener {
            modifyEvent()
        }

        return binding.root
    }


    private fun getEvent(){
        val args = this.arguments
        val id = args?.getInt("id").toString().trim { it <= ' ' }
        val StringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URLstring,
            Response.Listener{ response ->
                val userDetail = JSONArray(response)
                for (i in 0 until userDetail.length()) {
                    val dataobj = userDetail.getJSONObject(i)

                    editTextEventDescription!!.text = dataobj.get("event_description").toString();
                    editTextMeals!!.text = dataobj.get("meal_quantity").toString();
                    editTextEventTitle!!.text = dataobj.getString("event_title");
                    val imageUrl = "http://192.168.1.110/images/" + dataobj.getString("image")
                    Picasso.get().load(imageUrl).into(image)
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
                data["id"] = id!!
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(binding.root.context)
        requestQueue.add(StringRequest)
    }

    private fun modifyEvent() {

        val args = this.arguments
        val id = args?.getInt("id")!!.toString().trim{ it <= ' ' }
        val eventDescription = editTextEventDescription!!.text.toString().trim{ it <= ' ' }
        val eventMeal = editTextMeals!!.text.toString().trim{ it <= ' ' }
        val eventTitle = editTextEventTitle!!.text.toString().trim{ it <= ' ' }

        val request: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                Toast.makeText(binding.root.context, response, Toast.LENGTH_LONG).show()
                val intent = Intent(binding.root.context, AdminMainActivity::class.java)
                startActivity(intent)
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
                map["event_title"] = eventTitle
                map["event_description"] = eventDescription
                map["meal_quantity"] = eventMeal
                return map
            }
        }
        val queue = Volley.newRequestQueue(binding.root.context)
        queue.add(request)
    }
}