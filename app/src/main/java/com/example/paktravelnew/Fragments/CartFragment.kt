package com.example.paktravelnew.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paktravelnew.Adapters.placeAdapter
import com.example.paktravelnew.Adapters.tourAdapter
import com.example.paktravelnew.MainActivity
import com.example.paktravelnew.Models.PlaceModel
import com.example.paktravelnew.Models.TourModel
import com.example.paktravelnew.R
import com.example.paktravelnew.databinding.FragmentCartBinding
import com.example.paktravelnew.databinding.FragmentTourBottomSheetBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private val placeItems: ArrayList<PlaceModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        binding.buttonBack.setOnClickListener {
            val intent= Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseReference = FirebaseDatabase.getInstance().reference
        retrievePlaceItem()
    }

    private fun retrievePlaceItem() {
        database = FirebaseDatabase.getInstance()
        val placeRef: DatabaseReference = database.reference.child("Places")

        // Fetch data from the database
        placeRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear existing data before populating
                placeItems.clear()

                for (placeSnapshot in snapshot.children) {
                    val tourItem = placeSnapshot.getValue(PlaceModel::class.java)
                    tourItem?.let {
                        placeItems.add(it)
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
        val adapter = placeAdapter(requireContext(), placeItems, databaseReference)
        binding.placesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.placesRecyclerView.adapter = adapter
    }

}