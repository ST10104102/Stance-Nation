package com.example.stancenation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

private lateinit var bundle:Bundle

class CardAdapter (private val itemList : ArrayList<CardModel>, private val context: Context,private val intent:Intent) : RecyclerView.Adapter<CardAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.viewcard,
            parent, false
        )

        bundle = intent.extras!!

        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = itemList[position]

        holder.cardLabel.text = currentitem.cardholder
        holder.cardNumber.text = currentitem.cardnumber

        holder.cardView.setOnClickListener {
            val intent = Intent(context, PaymentGatewayActivity::class.java)
            intent.putExtra("CardHolder", currentitem.cardholder)
            intent.putExtra("CardNumber", currentitem.cardnumber)
            intent.putExtra("Price",bundle.getString("Price").toString())
            intent.putExtra("TicketPurpose",bundle.getString("TicketPurpose").toString())
            intent.putExtra("EventName",bundle.getString("EventName").toString())
            intent.putExtra("EventImage",bundle.getString("EventImage").toString())
            intent.putExtra("EventDate",bundle.getString("EventDate").toString())
            intent.putExtra("EventLocation",bundle.getString("EventLocation").toString())
            intent.putExtra("EventTime",bundle.getString("EventTime").toString())
            intent.putExtra("TicketStatus",bundle.getString("TicketStatus").toString())
            intent.putExtra("vehicleVin",bundle.getString("vehicleVin").toString())
            intent.putExtra("vehicleImage",bundle.getString("vehicleImage").toString())
            intent.putExtra("carMake",bundle.getString("carMake").toString())
            intent.putExtra("carModel",bundle.getString("carModel").toString())
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardLabel: TextView = itemView.findViewById(R.id.CardHolderLabel)
        val cardNumber: TextView = itemView.findViewById(R.id.CardLast4Digits)
        val cardView : CardView = itemView.findViewById(R.id.cardsCardview)

    }
}