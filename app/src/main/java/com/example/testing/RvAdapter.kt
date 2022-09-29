package com.example.testing

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_one.view.*

class RvAdapter (private val mList: List<ItemsViewModel>, private val onClickListener: onClickListener) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    private val URL = "http://localhost/images"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_one, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        val imageUrl = "http://192.168.1.110/images/" + ItemsViewModel.image
        Picasso.get().load(imageUrl).into(holder.itemView.image)
        holder.itemView.textViewTitle.text = ItemsViewModel.textViewTitle
        holder.itemView.textViewMeal.text = ItemsViewModel.textViewMeal
        holder.eventID = ItemsViewModel.id
        holder.eventDescription = ItemsViewModel.eventDescription
        holder.currentMeal = ItemsViewModel.currentMeal
        holder.itemView.eventProgressBar.max = ItemsViewModel.textViewMeal.toInt()
        ObjectAnimator.ofInt(holder.itemView.eventProgressBar, "progress", ItemsViewModel.currentMeal).start()


        holder.itemView.setOnClickListener{
            onClickListener.onItemClick(position)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView){
        val image: ImageView = itemView.findViewById(R.id.image)
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewMeal: TextView = itemView.findViewById(R.id.textViewMeal)
        var eventID: Int? = null
        var eventDescription: String? = null
        var currentMeal: Int? = null
        val progressBar: ProgressBar = itemView.findViewById(R.id.eventProgressBar)
    }
}