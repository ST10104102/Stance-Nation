package com.example.stancenation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ViewCards : AppCompatActivity() {
    private lateinit var dbref: DatabaseReference
    private lateinit var itemRecyclerview: RecyclerView
    private lateinit var itemArrayList: ArrayList<CardModel>
    private lateinit var itemArrayReverseList: ArrayList<CardModel>
    private lateinit var adapter: CardAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var scrollView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_cards)
        supportActionBar?.title = "Available Cards"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_arrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val addCard = findViewById<ImageButton>(R.id.add_btn)
        addCard.setOnClickListener {
            val intent = Intent(this, Cards::class.java)
            startActivity(intent)
        }
        scrollView = findViewById(R.id.scrollViewCards)
        itemRecyclerview = scrollView
        itemRecyclerview.layoutManager = LinearLayoutManager(this)
        itemRecyclerview.setHasFixedSize(true)
        itemArrayList = arrayListOf()
        itemArrayReverseList = arrayListOf()
        adapter = CardAdapter(itemArrayList,this,intent)
        itemRecyclerview.adapter = adapter
        progressBar = findViewById(R.id.ProgressBar)
        getUserData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getUserData() {
        progressBar.visibility = View.VISIBLE
        dbref = FirebaseDatabase.getInstance().getReference("Cards")
        dbref.child(Firebase.auth.currentUser?.uid!!).get().addOnCompleteListener {
            var x=1
            if(it.isSuccessful){
                for (itemSnapshot in it.result.children) {
                    val item = itemSnapshot.getValue(CardModel::class.java)
                    if (item != null) {
                        item.cardnumber = "**** "+item.cardnumber?.get(12).toString()+item.cardnumber?.get(13).toString()+item.cardnumber?.get(14).toString()+item.cardnumber?.get(15).toString()
                    }
                    itemArrayReverseList.add(item!!)
                    x++
                }
                for((z) in itemArrayReverseList.withIndex()){
                    itemArrayList.add(itemArrayReverseList[itemArrayReverseList.size-(z+1)])
                }
                if(!it.result.hasChildren()){
                    progressBar.visibility = View.GONE
                }else{
                    progressBar.visibility = View.GONE
                }
                adapter.notifyDataSetChanged()
            }else{
                progressBar.visibility = View.GONE
            }
        }

    }

}