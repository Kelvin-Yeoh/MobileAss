package com.example.testing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.testing.databinding.FragmentDonationBinding



class DonationActivity : Fragment() {

    lateinit var seekbar : SeekBar
    private var textTitle: TextView? = null
    private var textMYR: TextView? = null
    private var textMeal: TextView? = null
    private var btnMakeDonate: Button? = null
    private var myr: Float? = null
    private var currentMeal: Int? = null
    private var currentMealAfterDonation: Int? = null

    private lateinit var binding : FragmentDonationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_donation, container, false)
        val args = this.arguments

        textTitle = binding.textTitle
        seekbar = binding.seekBar
        textMeal = binding.textChangeMealQuantity
        textMYR = binding.textChangeMYR
        btnMakeDonate = binding.btnMakeDonate

        textTitle!!.text = args?.getString("textTitle")
        seekbar.max = 50

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                val progress = p1 * 10;
                textMeal!!.text = progress.toString() + " meals"
                myr = (progress * 3.5).toFloat()
                textMYR!!.text = "MYR " + myr.toString()
                btnMakeDonate!!.text = "Donate " + progress.toString() + " meanls"
                currentMeal = progress
                currentMealAfterDonation = args?.getInt("currentMeal")!! + progress
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
        btnMakeDonate!!.setOnClickListener {
            if (seekbar!!.progress > 0) {
            val bundle = Bundle()
            bundle.putString("image", args?.getString("image"))
            bundle.putString("textTitle", args?.getString("textTitle"))
            bundle.putString("textMeal", textMeal!!.text.toString())
            bundle.putInt("id", args?.getInt("id")!!)
            bundle.putString("eventDescription", args?.getString("eventDescription"))
            bundle.putInt("donationMeal", currentMeal!!)
            bundle.putInt("currentMealAfterDonation", currentMealAfterDonation!!)
            bundle.putString("totalMYR", textMYR!!.text.toString())

            val fragment = DonatePaymentActivity()
            fragment.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.framelayout,fragment)?.commit()
            } else {
                Toast.makeText(binding.root.context, "Danation cannot be 0 !", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }
}