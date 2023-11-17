package com.example.stancenation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class TicketDetails : AppCompatActivity() {
    private lateinit var vehicleVin:String
    private lateinit var vehicleImage:String
    private lateinit var carMake:String
    private lateinit var carModel:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_details)
        supportActionBar?.title = "Ticket Details"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_arrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val name = findViewById<TextView>(R.id.TicketLabel)
        val ticketImage = findViewById<ImageView>(R.id.TicketImage)
        val status = findViewById<TextView>(R.id.TicketStatusDetails)
        val date = findViewById<TextView>(R.id.TicketDateDetails)
        val location = findViewById<TextView>(R.id.TicketLocation)
        val price = findViewById<TextView>(R.id.TicketPrice)
        val purpose = findViewById<TextView>(R.id.TicketPurpose)
        val time = findViewById<TextView>(R.id.TicketTime)
        val vehVisible = findViewById<TextView>(R.id.TicketVehVisibility)
        val bundle = intent.extras
        if (bundle != null) {
            name.text = bundle.getString("EventName")
            Glide.with(this).load(bundle.getString("EventImage")).into(ticketImage)
            status.text =  bundle.getString("TicketStatus")
            date.text = bundle.getString("EventDate")
            location.text = bundle.getString("EventLocation")
            price.text = bundle.getString("EventPrice")
            purpose.text = bundle.getString("TicketPurpose")
            time.text = bundle.getString("EventTime")
            vehicleVin = bundle.getString("VehicleVin").toString()
            vehicleImage = bundle.getString("VehicleImage").toString()
            carMake = bundle.getString("CarMake").toString()
            carModel = bundle.getString("CarModel").toString()
        }

        if(purpose.text!="INDIVIDUAL: Spectator"){
            vehVisible.visibility = View.VISIBLE
        }

        vehVisible.setOnClickListener {
            val intent = Intent(this, VehicleDetails::class.java)
            intent.putExtra("VehicleVin",vehicleVin)
            intent.putExtra("VehicleImage",vehicleImage)
            intent.putExtra("CarMake",carMake)
            intent.putExtra("CarModel",carModel)
            startActivity(intent)
        }

    }
}