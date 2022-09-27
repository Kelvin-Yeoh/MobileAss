package com.example.testing

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EventDetail: AppCompatActivity() {

    private var textTitle: TextView? = null
    private var textMeal: TextView? = null
    private var image: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_detail)
        textTitle = findViewById(R.id.textViewEvent)
        textMeal = findViewById(R.id.textViewEvent)
        image = findViewById(R.id.image)

        val textTitles = intent.getStringExtra("textTitle")
        textTitle!!.text = textTitles
    }
}