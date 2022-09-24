package com.example.testing

import android.content.Context
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_one.view.*
import java.net.URL

class RvAdapter (private val mList: List<ItemsViewModel>) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    private val URL = "http://localhost/images"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_one, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        val imageUrl = "http://localhost/images/" + ItemsViewModel.image
        holder.itemView.image.setImageURI(Uri.parse(imageUrl))
        holder.itemView.textViewEvent.text = ItemsViewModel.textViewMeal
        holder.itemView.textViewMeal.text = ItemsViewModel.textViewMeal
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val textViewEvent: TextView = itemView.findViewById(R.id.textViewEvent)
        val textViewMeal: TextView = itemView.findViewById(R.id.textViewMeal)
    }
}