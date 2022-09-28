package com.example.testing

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
import kotlinx.android.synthetic.main.rv_one.*
import kotlinx.android.synthetic.main.rv_one.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() ,onClickListener{

    private val URLstring = "http://10.0.2.2/getevent.php"
    private lateinit var rvAdapter : RvAdapter
    private var recyclerView: RecyclerView? = null
    val data = ArrayList<ItemsViewModel>()
    private var testing: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donate_screen)
        title = "KotlinApp"

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
                val adapter = RvAdapter(data, this)
                recyclerview.adapter = adapter
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    this@MainActivity,
                    error.toString().trim { it <= ' ' },
                    Toast.LENGTH_SHORT
                ).show()
            }) {
        }
        val requestQueue = Volley.newRequestQueue(applicationContext)
        requestQueue.add(stringRequest)
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Event " +position+ " Clicked", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, EventDetail::class.java)
        intent.putExtra("image", data[position].image)
        intent.putExtra("textTitle", data[position].textViewTitle)
        intent.putExtra("textMeal", data[position].textViewMeal)
        intent.putExtra("id", data[position].id)
        intent.putExtra("eventDescription", data[position].eventDescription)
        startActivity(intent)
    }

    fun register(view: View?) {
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
        finish()
    }

    fun login(view: View?) {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }
}

