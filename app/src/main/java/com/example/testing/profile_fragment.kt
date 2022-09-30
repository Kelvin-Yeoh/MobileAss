package com.example.testing

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import com.example.testing.databinding.FragmentProfileFragmentBinding

class profile_fragment : Fragment() {

    lateinit var etEmail: TextView
    lateinit var etPassword: TextView
    lateinit var etUsername: TextView

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentProfileFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_fragment, container, false)

        etEmail = binding.lblEmailtesting
        etUsername = binding.lblEmailtesting
        etPassword = binding.lblEmailtesting

        return binding.root
    }
}