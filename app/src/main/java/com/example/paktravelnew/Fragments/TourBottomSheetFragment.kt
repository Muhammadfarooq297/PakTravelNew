package com.example.paktravelnew.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paktravelnew.Adapters.tourAdapter
import com.example.paktravelnew.MainActivity
import com.example.paktravelnew.Models.TourModel
import com.example.paktravelnew.R
import com.example.paktravelnew.databinding.FragmentTourBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TourBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentTourBottomSheetBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private val tourItems: ArrayList<TourModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTourBottomSheetBinding.inflate(inflater, container, false)
        binding.buttonBack.setOnClickListener {
            val intent= Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseReference = FirebaseDatabase.getInstance().reference
        retrieveTourItem()
    }

    private fun retrieveTourItem() {
        database = FirebaseDatabase.getInstance()
        val tourRef: DatabaseReference = database.reference.child("tours")

        // Fetch data from the database
        tourRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear existing data before populating
                tourItems.clear()

                for (tourSnapshot in snapshot.children) {
                    val tourItem = tourSnapshot.getValue(TourModel::class.java)
                    tourItem?.let {
                        tourItems.add(it)
                    }
                }

                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled if needed
            }
        })
    }

    private fun setAdapter() {
        val adapter = tourAdapter(requireContext(), tourItems, databaseReference)
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter
    }
}
