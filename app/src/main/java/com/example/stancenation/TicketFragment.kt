package com.example.stancenation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stancenation.databinding.FragmentTicketBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class TicketFragment : Fragment() {

    private var _binding: FragmentTicketBinding? = null

    private lateinit var dbref: DatabaseReference
    private lateinit var itemRecyclerview: RecyclerView
    private lateinit var itemArrayList: ArrayList<TicketModel>
    private lateinit var itemArrayReverseList: ArrayList<TicketModel>
    private lateinit var adapter: TicketAdapter
    private lateinit var noText: TextView
    private lateinit var progressBar: ProgressBar

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("Lifecycle")
    override fun onStart() {
        super.onStart()
        itemRecyclerview = binding.scrollView
        itemRecyclerview.layoutManager = LinearLayoutManager(binding.root.context)
        itemRecyclerview.setHasFixedSize(true)
        itemArrayList = arrayListOf()
        itemArrayReverseList = arrayListOf()
        adapter = TicketAdapter(itemArrayList, binding.root.context)
        itemRecyclerview.adapter = adapter
        progressBar = binding.ProgressBar
        getUserData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getUserData() {
        progressBar.visibility = View.VISIBLE
        noText = binding.NoTicketsText
        dbref = FirebaseDatabase.getInstance().getReference("Tickets")
        dbref.child(Firebase.auth.currentUser?.uid!!).get().addOnCompleteListener {
            var x=1
            if(it.isSuccessful){
                for (itemSnapshot in it.result.children) {
                    val item = itemSnapshot.getValue(TicketModel::class.java)
                    if (item != null) {
                        item.ticketNo = "#$x"
                    }
                    itemArrayReverseList.add(item!!)
                    x++
                }
                for((z) in itemArrayReverseList.withIndex()){
                    itemArrayList.add(itemArrayReverseList[itemArrayReverseList.size-(z+1)])
                }
                if(!it.result.hasChildren()){
                    noText.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }else{
                    noText.visibility = View.GONE
                    progressBar.visibility = View.GONE
                }
                adapter.notifyDataSetChanged()
            }else{
                noText.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }

    }

}