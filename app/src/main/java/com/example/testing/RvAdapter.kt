package com.example.testing

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
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_one.view.*
import java.net.URL

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
        holder.itemView.image.setImageURI(Uri.parse(imageUrl))
        holder.itemView.textViewEvent.text = ItemsViewModel.textViewMeal
        holder.itemView.textViewMeal.text = ItemsViewModel.textViewMeal

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
        val textViewEvent: TextView = itemView.findViewById(R.id.textViewEvent)
        val textViewMeal: TextView = itemView.findViewById(R.id.textViewMeal)
    }


}