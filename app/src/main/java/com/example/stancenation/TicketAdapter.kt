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

class TicketAdapter(private val itemList : ArrayList<TicketModel>, private val context: Context) : RecyclerView.Adapter<TicketAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.viewticket,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = itemList[position]

        holder.ticketNo.text = currentitem.ticketNo
        holder.ticketName.text = currentitem.eventName
        Glide.with(context).load(currentitem.eventImage).into(holder.ticketImage)
        holder.ticketStatus.text = currentitem.ticketStatus
        holder.ticketDate.text = currentitem.eventDate
        holder.ticketLocation.text = currentitem.eventLoc
        holder.ticketPrice.text = currentitem.eventPrice
        holder.ticketTime.text = currentitem.eventTime
        holder.ticketPurpose.text = currentitem.ticketPurpose
        holder.ticketVin.text = currentitem.vehicleVinNo
        Glide.with(context).load(currentitem.vehicleImage).into(holder.ticketImageVeh)
        holder.ticketCarMake.text = currentitem.carMake
        holder.ticketCarModel.text = currentitem.carModel

        holder.ticketCardview.setOnClickListener{
            val intent = Intent(context,TicketDetails::class.java)
            intent.putExtra("EventName",currentitem.eventName)
            intent.putExtra("EventImage",currentitem.eventImage)
            intent.putExtra("TicketStatus",currentitem.ticketStatus)
            intent.putExtra("EventDate",currentitem.eventDate)
            intent.putExtra("EventLocation",currentitem.eventLoc)
            intent.putExtra("EventPrice",currentitem.eventPrice)
            intent.putExtra("EventTime",currentitem.eventTime)
            intent.putExtra("TicketPurpose",currentitem.ticketPurpose)
            intent.putExtra("VehicleVin",currentitem.vehicleVinNo)
            intent.putExtra("VehicleImage",currentitem.vehicleImage)
            intent.putExtra("CarMake",currentitem.carMake)
            intent.putExtra("CarModel",currentitem.carModel)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val ticketNo : TextView = itemView.findViewById(R.id.TicketNo)
        val ticketName : TextView = itemView.findViewById(R.id.TicketEventName)
        val ticketImage : ImageView = itemView.findViewById(R.id.TicketEventImage)
        val ticketStatus : TextView = itemView.findViewById(R.id.TicketStatus)
        val ticketDate : TextView = itemView.findViewById(R.id.TicketEventDate)
        val ticketLocation : TextView = itemView.findViewById(R.id.TicketEventLocation)
        val ticketPrice : TextView = itemView.findViewById(R.id.TicketEventPrice)
        val ticketTime : TextView = itemView.findViewById(R.id.TicketEventTime)
        val ticketPurpose : TextView = itemView.findViewById(R.id.TicketEventPurpose)
        val ticketVin : TextView = itemView.findViewById(R.id.TicketEventVin)
        val ticketImageVeh : ImageView = itemView.findViewById(R.id.TicketEventVehImage)
        val ticketCarMake : TextView = itemView.findViewById(R.id.TicketEventCarMake)
        val ticketCarModel : TextView = itemView.findViewById(R.id.TicketEventCarModel)
        val ticketCardview : CardView = itemView.findViewById(R.id.cardviewTicket)

    }

}