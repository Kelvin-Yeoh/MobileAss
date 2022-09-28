package com.example.testing

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_one.view.*

class EventDetail: AppCompatActivity() {

    private var textTitle: TextView? = null
    private var textMeal: TextView? = null
    private var textDescription: TextView? = null
    private var image: ImageView? = null
    private var eventID: Int? = null
    var btnDonate: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_detail)
        textTitle = findViewById(R.id.textViewTitle)
        textMeal = findViewById(R.id.textViewMeal)
        image = findViewById(R.id.image)
        textDescription = findViewById(R.id.textViewDescription)
        btnDonate = findViewById(R.id.btnDonate)

        val imageUrl = "http://192.168.1.110/images/" + intent.getStringExtra("image")
        Picasso.get().load(imageUrl).into(image)
        textTitle!!.text = intent.getStringExtra("textTitle")
        textMeal!!.text = intent.getStringExtra("textMeal")
        textDescription!!.text = intent.getStringExtra("eventDescription")

        btnDonate!!.setOnClickListener {
            val intent = Intent(this, DonationActivity::class.java)
            startActivity(intent)
        }
    }
}