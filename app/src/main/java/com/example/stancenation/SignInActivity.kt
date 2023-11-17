package com.example.stancenation

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        auth = Firebase.auth
        signUpFunctionality()
        loginFunctionality()
    }

    public override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //val builder = AlertDialog.Builder(this,1)
            //builder.setCancelable(false)
            //builder.setView(R.layout.progress_layout)
            ObjectAnimator.ofInt(R.layout.progress_layout, "progress",0,1)
                .setDuration(7000)
                .start()
            //val dialog = builder.create()
            //dialog.show()
            val intent = Intent(this, EventHomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signUpFunctionality() {
        val buttonClick = findViewById<TextView>(R.id.signupText)
        buttonClick.setOnClickListener {
            val builder = AlertDialog.Builder(this,1)
            builder.setCancelable(false)
            builder.setView(R.layout.progress_layout)
            val dialog = builder.create()
            dialog.show()
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }
    }

    private fun loginFunctionality() {
        val buttonClick2 = findViewById<Button>(R.id.loginButton)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        buttonClick2.setOnClickListener {
            val emailtxt = email.text.toString()
            val passwordtxt = password.text.toString()
            if (emailCheck(emailtxt)) {
                if (passwordCheck(passwordtxt)) {
                    val builder = AlertDialog.Builder(this,1)
                    builder.setCancelable(false)
                    builder.setView(R.layout.progress_layout)
                    val dialog = builder.create()
                    dialog.show()
                    auth.signInWithEmailAndPassword(emailtxt, passwordtxt)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                dialog.show()
                                val intent = Intent(this, EventHomeActivity::class.java)
                                startActivity(intent)
                            } else {
                                // If sign in fails, display a message to the user.
                                dialog.cancel()
                                Toast.makeText(
                                    baseContext,
                                    "Authentication failed.",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                        }
                }
            }
        }
    }

    private fun emailCheck(email: String): Boolean {
        if (email.isEmpty()) {
            showAlert("Email field is empty")
        } else {
            return true
        }
        return false
    }

    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setPositiveButton("Ok", null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun passwordCheck(password: String): Boolean {
        if (password.isEmpty()) {
            showAlert("Password field is empty")
        } else {
            return true
        }
        return false
    }

}