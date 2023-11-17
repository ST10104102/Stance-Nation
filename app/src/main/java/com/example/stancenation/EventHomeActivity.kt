package com.example.stancenation

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class EventHomeActivity : AppCompatActivity() {
    private lateinit var dbref: DatabaseReference
    private lateinit var itemRecyclerview: RecyclerView
    private lateinit var itemArrayList: ArrayList<EventModel>
    private lateinit var adapter: MyAdapter
    private lateinit var progressBar: ProgressBar
    private  var eventListener: ValueEventListener? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.title = "Home"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_home)
        val lastPage = findViewById<ImageButton>(R.id.back_btn)
        itemRecyclerview = findViewById(R.id.scrollView2)
        itemRecyclerview.layoutManager = LinearLayoutManager(this)
        itemRecyclerview.setHasFixedSize(true)
        itemArrayList = arrayListOf()
        adapter = MyAdapter(itemArrayList, this)
        itemRecyclerview.adapter = adapter
        getUserData()

        lastPage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getUserData() {
        progressBar = findViewById(R.id.ProgressBar)
        dbref = FirebaseDatabase.getInstance().getReference("Events")
        progressBar.visibility = View.VISIBLE

        eventListener = dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                itemArrayList.clear()
                for (itemSnapshot in snapshot.children) {
                    val dataClass = itemSnapshot.getValue(EventModel::class.java)
                    if (dataClass != null) {
                        itemArrayList.add(dataClass)
                    }
                }
                adapter.notifyDataSetChanged()
                progressBar.visibility = View.GONE
            }
            override fun onCancelled(error: DatabaseError) {
                progressBar.visibility = View.GONE
            }
        })
    }

}