package com.example.stancenation

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ParticipateTicketActivity : AppCompatActivity() {
    private var imageuri: Uri? = null
    private lateinit var register_price:String
    private lateinit var normal_price:String
    private lateinit var eventName:String
    private var eventImage: String? = ""
    private var eventDate: String? = ""
    private var eventLocation: String? = ""
    private var eventTime: String? = ""
    private var ticketStatus: String?=""
    private lateinit var progressBar: ProgressBar
    private lateinit var vehicleImage:ImageView
    private lateinit var vehicleVin:EditText
    private lateinit var carMake:EditText
    private lateinit var carModel:EditText

    private lateinit var vehicleimage:String
    private lateinit var dbRefImage: StorageReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participate_ticket)
        supportActionBar?.title = "Purchase Ticket"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_arrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        vehicleImage = findViewById(R.id.upload_car)
        vehicleVin = findViewById(R.id.VehicleVinText)
        carMake = findViewById(R.id.CarMakeText)
        carModel = findViewById(R.id.CarModelText)
        progressBar = findViewById(R.id.ProgressBar)

        val resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                imageuri = result.data?.data
                vehicleImage.setImageURI(imageuri)
            } else {
                Toast.makeText(this, "No image Selected", Toast.LENGTH_LONG).show()
            }
        }

         vehicleImage.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            resultLauncher.launch(intent)
        }

        val bundle = intent.extras
        if (bundle != null) {
            normal_price = bundle.getString("Price").toString()
            register_price = bundle.getString("RegisterPrice").toString()
            eventName = bundle.getString("EventName").toString()
            eventImage = bundle.getString("EventImage")
            eventDate = bundle.getString("EventDate")
            eventLocation = bundle.getString("EventLocation")
            eventTime = bundle.getString("EventTime")
            ticketStatus = bundle.getString("TicketStatus")
        }

        val buttonregister = findViewById<Button>(R.id.register_Car)
        buttonregister.setOnClickListener {
            vehicleimage=""
if(CheckNull(vehicleVin.text.toString(),"Vehicle Vin cannot be empty")) {
    if(CheckLen(vehicleVin.text.toString(),17,"Vin Number should be 17 Charaters")){
    if (CheckNull(carMake.text.toString(), "Car Make cannot be empty")) {
        if (CheckNull(carModel.text.toString(), "Car Model cannot be empty")) {
            try {
                progressBar.visibility = View.VISIBLE
                uploadImage()
            } catch (ex: java.lang.Exception) {
                showAlert("Please Select an image")
                progressBar.visibility = View.GONE
            }
        }
    }
    }
}
        }

        val buttonbuy = findViewById<Button>(R.id.skip_Register)
        buttonbuy.setOnClickListener {
            val intent = Intent(this, ViewCards::class.java)
            intent.putExtra("Price",normal_price)
            intent.putExtra("TicketPurpose","INDIVIDUAL: Spectator")
            intent.putExtra("EventName",eventName)
            intent.putExtra("EventImage",eventImage)
            intent.putExtra("EventDate",eventDate)
            intent.putExtra("EventLocation",eventLocation)
            intent.putExtra("EventTime",eventTime)
            intent.putExtra("TicketStatus",ticketStatus)
            intent.putExtra("vehicleVin","")
            intent.putExtra("vehicleImage","")
            intent.putExtra("carMake","")
            intent.putExtra("carModel","")
            startActivity(intent)
        }

    }

    private fun getFileExtension(FileEx: Uri): String? {
        val contentresolver = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getMimeTypeFromExtension(contentresolver.getType(FileEx))
    }

    private fun uploadImage(){
        dbRefImage = FirebaseStorage.getInstance().getReference("UserCars")
        dbRefImage = dbRefImage.child(
            System.currentTimeMillis().toString() + "." + getFileExtension(imageuri!!)
        )
        if (imageuri != null) {
            imageuri?.let {
                dbRefImage.putFile(it)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            dbRefImage.downloadUrl.addOnCompleteListener { uri ->
                                if (uri.isSuccessful) {
                                    vehicleimage = "https://firebasestorage.googleapis.com"+uri.result.encodedPath+"?alt=media"
                                        val intent = Intent(this, ViewCards::class.java)
                                        intent.putExtra("Price", register_price)
                                        intent.putExtra("TicketPurpose", "INDIVIDUAL: Participator")
                                        intent.putExtra("EventName", eventName)
                                        intent.putExtra("EventImage", eventImage)
                                        intent.putExtra("EventDate", eventDate)
                                        intent.putExtra("EventLocation", eventLocation)
                                        intent.putExtra("EventTime", eventTime)
                                        intent.putExtra("TicketStatus", ticketStatus)
                                        intent.putExtra("vehicleVin", vehicleVin.text.toString())
                                        intent.putExtra("vehicleImage", vehicleimage)
                                        intent.putExtra("carMake", carMake.text.toString())
                                        intent.putExtra("carModel", carModel.text.toString())
                                        startActivity(intent)
                                }else{
                                    progressBar.visibility = View.GONE
                                }
                            }
                        }
                    }
            }
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
        } else {
            return true
        }
        return false
    }

    private fun CheckNull(text: String): Boolean {
        if (text.isEmpty()){
            return false
        }
        return true
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