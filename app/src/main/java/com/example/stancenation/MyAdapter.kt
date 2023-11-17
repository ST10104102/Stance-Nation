package com.example.stancenation

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class MyAdapter(private val itemList : ArrayList<EventModel>,private val context: Context) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.eventdetails,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = itemList[position]

        holder.eventShow.text = currentitem.eventName
        holder.eventPrice.text = currentitem.eventPrice
        holder.eventDate.text = currentitem.eventDate
        //Picasso.get().load(currentitem.imageuri).into(holder.imageview)
        Glide.with(context).load(currentitem.eventImage).into(holder.imageview)
        holder.cardview.setOnClickListener{
            val intent = Intent(context,ViewEventActivity::class.java)
            intent.putExtra("Name", currentitem.eventName)
            intent.putExtra("Price", currentitem.eventPrice)
            intent.putExtra("Date", currentitem.eventDate)
            intent.putExtra("Image", currentitem.eventImage)
            intent.putExtra("Price2",currentitem.eventPrice2)
            intent.putExtra("Location",currentitem.eventLocation)
            intent.putExtra("Time",currentitem.eventTime)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val eventShow : TextView = itemView.findViewById(R.id.eventShow)
        val eventPrice : TextView = itemView.findViewById(R.id.eventPrice)
        val eventDate : TextView = itemView.findViewById(R.id.eventDate)
        val imageview : ImageView = itemView.findViewById(R.id.imageView)
        val cardview : CardView = itemView.findViewById(R.id.cardDetails)
    }

}