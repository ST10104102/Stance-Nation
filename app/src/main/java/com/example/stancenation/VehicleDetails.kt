package com.example.stancenation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class VehicleDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_details)
        supportActionBar?.title = "Vehicle Details"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_arrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val vehImage = findViewById<ImageView>(R.id.VehicleImage)
        val vehVin = findViewById<TextView>(R.id.VehicleVin)
        val carMake = findViewById<TextView>(R.id.CarMake)
        val carModel = findViewById<TextView>(R.id.CarModel)
        val bundle = intent.extras
        if (bundle != null) {
            vehVin.text = bundle.getString("VehicleVin")
            Glide.with(this).load(bundle.getString("VehicleImage")).into(vehImage)
            carMake.text = bundle.getString("CarMake")
            carModel.text = bundle.getString("CarModel")
        }
    }
}