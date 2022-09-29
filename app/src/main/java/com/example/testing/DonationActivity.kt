package com.example.testing

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_one.view.*
import java.util.*

class DonationActivity: AppCompatActivity() {

    lateinit var seekbar : SeekBar
    private var textMYR: TextView? = null
    private var textMeal: TextView? = null
    private var btnDonate: Button? = null
    private var myr: Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donation_screen)

        seekbar = findViewById(R.id.seekBar)
        textMeal = findViewById(R.id.textChangeMealQuantity)
        textMYR = findViewById(R.id.textChangeMYR)
        btnDonate = findViewById(R.id.btnMakeDonate)

        seekbar.max = 50

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                val progress = p1 * 10;
                textMeal!!.text = progress.toString() + " meals"
                myr = (progress * 3.5).toFloat()
                textMYR!!.text = "MYR " + myr.toString()
                btnDonate!!.text = "Donate " + progress.toString() + " meanls"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }
}