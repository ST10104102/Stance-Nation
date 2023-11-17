package com.example.stancenation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ViewEventActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private lateinit var progressBar: ProgressBar
    private var eventName: String? = ""
    private var eventImage: String? = ""
    private var eventDate: String? = ""
    private var eventPrice: String? = ""
    private var eventParticipatePrice: String? = ""
    private var eventLocation: String? = ""
    private var eventTime: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.title = "Event Details"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_arrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_event)
        val tik1 = findViewById<Button>(R.id.ticket1)
        val tik2 = findViewById<Button>(R.id.ticket2)
        progressBar = findViewById(R.id.ProgressBar)
        val image = findViewById<ImageView>(R.id.imageViewEvent)
        val show = findViewById<TextView>(R.id.textView19)
        val date = findViewById<TextView>(R.id.textView20)
        val time = findViewById<TextView>(R.id.text_time)
        val location = findViewById<TextView>(R.id.textView4)
        val priceN = findViewById<TextView>(R.id.textView22)
        val priceP = findViewById<TextView>(R.id.textView5)

        val bundle = intent.extras
        if (bundle != null) {
            eventName = bundle.getString("Name")
            eventImage = bundle.getString("Image")
            eventDate = bundle.getString("Date")
            eventPrice = bundle.getString("Price")
            eventParticipatePrice = bundle.getString("Price2")
            eventLocation = bundle.getString("Location")
            eventTime = bundle.getString("Time")
        }
        show.text = eventName
        Glide.with(this).load(eventImage).into(image)
        date.text = eventDate
        priceN.text = eventPrice
        priceP.text = eventParticipatePrice
        location.text = eventLocation
        time.text = eventTime

        tik1.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            auth = Firebase.auth
            addTicket()
        }
        tik2.setOnClickListener {
            val intent = Intent(this, ParticipateTicketActivity::class.java)
            intent.putExtra("RegisterPrice",eventParticipatePrice)
            intent.putExtra("Price",eventPrice)
            intent.putExtra("EventName",eventName)
            intent.putExtra("EventImage",eventImage)
            intent.putExtra("EventDate",eventDate)
            intent.putExtra("EventLocation",eventLocation)
            intent.putExtra("EventTime",eventTime)
            intent.putExtra("TicketStatus","STATUS: Bought")
            startActivity(intent)
        }
    }

    private fun addTicket() {
        dbRef = FirebaseDatabase.getInstance().getReference("Tickets")
        val item = TicketModel(
            "",
            eventName,
            eventImage,
            "STATUS: Reserved",
            eventDate,
            eventLocation,
            eventPrice,
            eventTime,
            "INDIVIDUAL: Spectator"
        )
        dbRef.child(Firebase.auth.currentUser?.uid!!)
            .child(dbRef.push().key!!).setValue(item)
            .addOnSuccessListener {
                Toast.makeText(
                    this,
                    "Ticket is Reserved",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                progressBar.visibility = View.GONE
            }
    }
}