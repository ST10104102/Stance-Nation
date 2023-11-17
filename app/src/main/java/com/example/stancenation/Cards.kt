package com.example.stancenation

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Cards : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    private lateinit var dbRef: DatabaseReference
    private lateinit var cardHolder: EditText
    private lateinit var cardNumber: EditText
    private lateinit var cardExpiry: EditText
    private lateinit var cardCvv: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards)
        supportActionBar?.title = "Add Card Details"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_arrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        progressBar = findViewById(R.id.ProgressBar)
        val addButton = findViewById<Button>(R.id.buttonCardAdd)
        cardHolder = findViewById(R.id.CardHolder)
        cardNumber = findViewById(R.id.CardNumber)
        cardExpiry = findViewById(R.id.CardExpiry)
        cardCvv = findViewById(R.id.CardCVV)

        addButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            auth = Firebase.auth
            if (CheckNull(cardHolder.text.toString(), "card holder cannot be null")) {
                if (CheckNull(cardNumber.text.toString(), "cardNumber must have a value")) {
                    if (CheckLen(
                            cardNumber.text.toString(),
                            16,
                            "card number should be at least 16 digits"
                        )
                    ) {
                        if (CheckNull(cardExpiry.text.toString(), "card Expiry cannot be empty")) {
                            if (CheckLen(
                                    cardExpiry.text.toString(),
                                    4,
                                    "Card expiry should be 4 digits"
                                )
                            ) {
                                if (CheckNull(
                                        cardCvv.text.toString(),
                                        "card cvv cannot be empty"
                                    )
                                ) {
                                    if (CheckLen(
                                            cardCvv.text.toString(),
                                            3,
                                            "cvv has to be 3 digits"
                                        )
                                    ) {
                                        addCard()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun addCard() {
        dbRef = FirebaseDatabase.getInstance().getReference("Cards")
        val item = CardModel(
            cardHolder.text.toString(),
            cardNumber.text.toString(),
            cardExpiry.text.toString(),
            cardCvv.text.toString()
        )
        dbRef.child(Firebase.auth.currentUser?.uid!!)
            .child(dbRef.push().key!!).setValue(item)
            .addOnSuccessListener {
                Toast.makeText(
                    this,
                    "Card added",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this, ViewCards::class.java)
                startActivity(intent)
                progressBar.visibility = View.GONE
            }
    }

    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setPositiveButton("Ok", null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun CheckNull(text: String, message: String): Boolean {
        if (text.isEmpty()) {
            showAlert(message)
            progressBar.visibility = View.GONE
        } else {
            return true
        }
        return false
    }

    private fun CheckLen(text: String, length: Int, message: String): Boolean {
        if (text.length < length) {
            showAlert(message)
        } else {
            return true
        }
        return false
    }

}