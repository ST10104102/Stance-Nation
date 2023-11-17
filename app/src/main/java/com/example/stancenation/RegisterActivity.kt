package com.example.stancenation

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = Firebase.auth
        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this){
            if(it){
                signUpFunctionality()
            }else{
                Toast.makeText(
                    baseContext,
                    "No Network",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }

    private fun signUpFunctionality() {
        val firstname = findViewById<EditText>(R.id.name)
        val surname = findViewById<EditText>(R.id.surname)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val cPassword = findViewById<EditText>(R.id.confirm_password)
        val buttonClick = findViewById<Button>(R.id.signUpButton)
        buttonClick.setOnClickListener {
            if (firstnameCheck(firstname.text.toString())) {
                if (surnameCheck(surname.text.toString())) {
                    if (emailCheck(email.text.toString())) {
                        if (passwordCheck(password.text.toString())) {
                            if (cPasswordCheck(
                                    password.text.toString(),
                                    cPassword.text.toString()
                                )
                            ) {
                                val builder = AlertDialog.Builder(this,1)
                                builder.setCancelable(false)
                                builder.setView(R.layout.progress_layout)
                                val dialog = builder.create()
                                dialog.show()
                                auth.createUserWithEmailAndPassword(
                                    email.text.toString(),
                                    password.text.toString()
                                )
                                    .addOnCompleteListener(this) { task ->
                                        if (task.isSuccessful) {
                                            // Sign in success, user signed up
                                            val user = Firebase.auth.currentUser
                                            val profileUpdates = userProfileChangeRequest {
                                                displayName = firstname.text.toString()
                                            }
                                            user!!.updateProfile(profileUpdates)
                                            auth.signInWithEmailAndPassword(
                                                email.text.toString(),
                                                password.text.toString()
                                            ).addOnSuccessListener {
                                                Intent(
                                                    this,
                                                    EventHomeActivity::class.java
                                                ).also { startActivity(it)
                                                dialog.dismiss()
                                                }
                                            }.addOnFailureListener {
                                                dialog.cancel()
                                                Toast.makeText(
                                                    baseContext,
                                                    "Something went wrong",
                                                    Toast.LENGTH_SHORT,
                                                ).show()
                                            }

                                        } else {
                                            dialog.cancel()
                                            // If sign in fails, display a message to the user.
                                            Toast.makeText(
                                                baseContext,
                                                "Something went wrong",
                                                Toast.LENGTH_SHORT,
                                            ).show()
                                        }
                                    }
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

    private fun firstnameCheck(firstname: String): Boolean {
        if (firstname.isEmpty()) {
            showAlert("Name field is empty")
        } else {
            return true
        }
        return false
    }

    private fun surnameCheck(surname: String): Boolean {
        if (surname.isEmpty()) {
            showAlert("Surname field is empty")
        } else {
            return true
        }
        return false
    }

    private fun emailCheck(email: String): Boolean {
        if (email.isEmpty()) {
            showAlert("Email field is empty")
        } else {
            return true
        }
        return false
    }

    private fun passwordCheck(password: String): Boolean {
        if (password.isEmpty()) {
            showAlert("Password field is empty")
        } else if (password.length < 8) {
            showAlert("Password should be 8 or more in length")
        } else {
            return true
        }
        return false
    }

    private fun cPasswordCheck(password: String, cpassword: String): Boolean {
        if (password.contentEquals(cpassword)) {
            return true
        } else {
            showAlert("Passwords do not match")
        }
        return false
    }


}