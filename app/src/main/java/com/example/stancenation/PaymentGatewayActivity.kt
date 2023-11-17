package com.example.stancenation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class PaymentGatewayActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private lateinit var progressBar: ProgressBar

    private var eventName: String? = ""
    private var eventImage: String? = ""
    private var eventDate: String? = ""
    private var eventPrice: String? = ""
    private var eventLocation: String? = ""
    private var eventTime: String? = ""
    private var ticketPurpose: String? = ""
    private var ticketStatus: String? = ""
    private var vehicleVin: String? = ""
    private var vehicleImage: String? = ""
    private var carMake: String? = ""
    private var carModel: String? = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_gateway)
        supportActionBar?.title = "Make Payment"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_arrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val cardholder = findViewById<TextView>(R.id.CardHolderTextViewValue)
        val cardnumber = findViewById<TextView>(R.id.CardNumberTextViewValue)
        val ticketprice = findViewById<TextView>(R.id.TicketPriceTextViewValue)
        val eventname = findViewById<TextView>(R.id.EventNameTextViewValue)
        val purchaseButton = findViewById<Button>(R.id.buttonPay)
        progressBar = findViewById(R.id.ProgressBar)

        val bundle = intent.extras
        if (bundle != null) {
            cardholder.text = bundle.getString("CardHolder")
            cardnumber.text = bundle.getString("CardNumber")
            ticketprice.text = bundle.getString("Price")
            eventname.text = bundle.getString("EventName")

            eventPrice = bundle.getString("Price").toString()
            ticketPurpose = bundle.getString("TicketPurpose").toString()
            eventName = bundle.getString("EventName").toString()
            eventImage = bundle.getString("EventImage").toString()
            eventDate = bundle.getString("EventDate").toString()
            eventLocation = bundle.getString("EventLocation").toString()
            eventTime = bundle.getString("EventTime").toString()
            ticketStatus = bundle.getString("TicketStatus").toString()
            vehicleVin = bundle.getString("vehicleVin").toString()
            vehicleImage = bundle.getString("vehicleImage").toString()
            carMake = bundle.getString("carMake").toString()
            carModel = bundle.getString("carModel").toString()

        }

        purchaseButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            auth = Firebase.auth
            addTicket()
        }

    }

    private fun addTicket() {
        dbRef = FirebaseDatabase.getInstance().getReference("Tickets")
        val item = TicketModel(
            "",
            eventName,
            eventImage,
            ticketStatus,
            eventDate,
            eventLocation,
            eventPrice,
            eventTime,
            ticketPurpose,
            vehicleVin,
            vehicleImage,
            carMake,
            carModel
        )
        dbRef.child(Firebase.auth.currentUser?.uid!!)
            .child(dbRef.push().key!!).setValue(item)
            .addOnSuccessListener {
                Toast.makeText(
                    this,
                    "Successful Purchase",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                progressBar.visibility = View.GONE
            }
    }

}