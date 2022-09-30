package com.example.testing

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.testing.databinding.FragmentEventDetailActivityBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_one.view.*


class EventDetailActivity : Fragment() {

    private lateinit var textTitle: TextView
    private var textMeal: TextView? = null
    private var textDescription: TextView? = null
    private var image: ImageView? = null
    private var eventID: Int? = null
    private var btnDonate: Button? = null
    private var progressBar : ProgressBar? = null


    private lateinit var binding : FragmentEventDetailActivityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_detail_activity, container, false)

        super.onCreate(savedInstanceState)
        textTitle = binding.textViewTitle
        textMeal = binding.textViewMeal
        image = binding.image
        textDescription = binding.textViewDescription
        btnDonate = binding.btnDonate
        progressBar = binding.progressBar

        val args = this.arguments

        val imageUrl = "http://192.168.1.110/images/" + args?.getString("image")
        Picasso.get().load(imageUrl).into(image)
        textTitle.text = args?.getString("textTitle")
        textMeal!!.text = args?.getString("textMeal")
        textDescription!!.text = args?.getString("eventDescription")
        progressBar!!.max = args?.getString("textMeal")!!.toInt()
        progressBar!!.progress = args?.getInt("currentMeal")!!

        btnDonate!!.setOnClickListener {
            val intent = Intent(binding.root.context, DonationActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

}