package com.example.testing

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.testing.databinding.FragmentAdminActivityBinding
import com.example.testing.databinding.FragmentEventViewActivityBinding


class adminActivity : Fragment() ,onClickListener{

    val URLstring = "http://10.0.2.2/getevent.php"
    private lateinit var rvAdapter : adminRvAdapter
    private var recyclerView: RecyclerView? = null
    val data = ArrayList<ItemsViewModel>()
    private var testing: TextView? = null
    private var btnViewMore: Button? = null

    private lateinit var binding : FragmentAdminActivityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_activity, container, false)
        // getting the recyclerview by its id
        recyclerView = binding.adminRecycler
        // this creates a vertical layout Manager
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        fetchingJSON()
        return binding.root
    }

    private fun fetchingJSON() {
        val recyclerview = binding.adminRecycler
        val stringRequest: JsonArrayRequest = object : JsonArrayRequest(
            Request.Method.GET, URLstring,
            null, { response ->

                for (i in 0 until response.length()) {
                    val dataobj = response.getJSONObject(i)

                    //adding the product to product list
                    data.add(
                        ItemsViewModel(
                            dataobj.getString("image"),
                            dataobj.getString("event_title"),
                            dataobj.getString("meal_quantity"),
                            dataobj.getInt("id"),
                            dataobj.getString("event_description"),
                            dataobj.getInt("current_meal_quantity")
                        )
                    )
                }
                val adapter = adminRvAdapter(data, this)
                recyclerview.adapter = adapter
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    binding.root.context,
                    error.toString().trim { it <= ' ' },
                    Toast.LENGTH_SHORT
                ).show()
            }) {
        }
        val requestQueue = Volley.newRequestQueue(context)

        requestQueue.add(stringRequest)

    }

    override fun onItemClick(position: Int) {
        val bundle = Bundle()
        bundle.putString("image", data[position].image)
        bundle.putString("textTitle", data[position].textViewTitle)
        bundle.putString("textMeal", data[position].textViewMeal)
        bundle.putInt("id", data[position].id)
        bundle.putString("eventDescription", data[position].eventDescription)
        bundle.putInt("currentMeal", data[position].currentMeal)

        val fragment = AdminModifyActivity()
        fragment.arguments = bundle
        fragmentManager?.beginTransaction()?.replace(R.id.framelayout,fragment)?.commit()
    }


}