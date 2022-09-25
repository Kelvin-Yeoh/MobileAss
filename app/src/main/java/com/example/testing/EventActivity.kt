package com.example.testing

import android.media.Image
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EventActivity: AppCompatActivity() {

    private var textTitle: TextView? = null
    private var textMeal: TextView? = null
    private var image: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_detail)
        textTitle = findViewById(R.id.textViewEvent)
        textMeal = findViewById(R.id.textViewEvent)
        image = findViewById(R.id.image)
    }
}