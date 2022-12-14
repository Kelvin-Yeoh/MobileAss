package com.example.testing

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.testing.databinding.FragmentEventDetailActivityBinding
import com.squareup.picasso.Picasso


class EventDetailActivity : Fragment() {

    private lateinit var textTitle: TextView
    private var textMeal: TextView? = null
    private var textDescription: TextView? = null
    private var image: ImageView? = null
    private var eventID: Int? = null
    private var btnDonate: Button? = null
    private var progressBar : ProgressBar? = null
    private lateinit var binding : FragmentEventDetailActivityBinding
    var PREFS_KEY = "prefs"
    var EMAIL_KEY = "email"
    var NAME_KEY = "name"
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_detail_activity, container, false)
        sharedPreferences =  binding.root.context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)

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
        textMeal!!.text = args?.getString("textMeal")  + " out of " + args?.getInt("currentMeal").toString()
        textDescription!!.text = args?.getString("eventDescription")
        progressBar!!.max = args?.getString("textMeal")!!.toInt()
        progressBar!!.progress = args?.getInt("currentMeal")!!

        btnDonate!!.setOnClickListener {
            val email = sharedPreferences.getString(EMAIL_KEY, null)!!
            var checkEmail = email
            if (checkEmail != null && checkEmail != ""){
                val bundle = Bundle()
                bundle.putString("image", args?.getString("image"))
                bundle.putString("textTitle", args?.getString("textTitle"))
                bundle.putString("textMeal", args?.getString("textMeal"))
                bundle.putInt("id", args?.getInt("id")!!)
                bundle.putString("eventDescription", args?.getString("eventDescription"))
                bundle.putInt("currentMeal", args?.getInt("currentMeal")!!)


                val fragment = DonationActivity()
                fragment.arguments = bundle
                fragmentManager?.beginTransaction()?.replace(R.id.framelayout, fragment)?.commit()
            } else {
                val fragment = LoginActivity()
                fragmentManager?.beginTransaction()?.replace(R.id.framelayout,fragment)?.commit()
            }
        }
        return binding.root
    }

}