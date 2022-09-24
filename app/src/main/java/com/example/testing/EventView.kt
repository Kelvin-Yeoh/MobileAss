package com.example.testing

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject


class EventView : AppCompatActivity() {

    private val URLstring = "http://10.0.2.2/getevent.php"
    private lateinit var rvAdapter : RvAdapter
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donate_screen)
        // getting the recyclerview by its id
        recyclerView = findViewById<RecyclerView>(R.id.recycler)
        // this creates a vertical layout Manager
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        fetchingJSON()
    }

    private fun fetchingJSON() {
        val recyclerview = findViewById<RecyclerView>(R.id.recycler)
        val stringRequest: JsonArrayRequest = object : JsonArrayRequest(
            Request.Method.GET, URLstring,
            null, { response ->
                val data = ArrayList<ItemsViewModel>()
                for (i in 0 until response.length()) {
                    val dataobj = response.getJSONObject(i)

                    //adding the product to product list
                    data.add(
                        ItemsViewModel(
                            dataobj.getString("image"),
                            dataobj.getString("event_description"),
                            dataobj.getString("meal_quantity")
                        )
                    )
                }
                val adapter = RvAdapter(data)
                recyclerview.adapter = adapter
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    this@EventView,
                    error.toString().trim { it <= ' ' },
                    Toast.LENGTH_SHORT
                ).show()
            }) {
        }
        val requestQueue = Volley.newRequestQueue(applicationContext)
        requestQueue.add(stringRequest)
    }
}

