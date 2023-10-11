package com.example.paktravelnew

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paktravelnew.Adapters.foodAdapter
import com.example.paktravelnew.Fragments.MenuFragment
import com.example.paktravelnew.Models.TourModel
import com.example.paktravelnew.Models.foodModel
import com.example.paktravelnew.databinding.ActivityExploreFoodBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import taimoor.sultani.sweetalert2.Sweetalert

class exploreFoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExploreFoodBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private val foodItems: ArrayList<foodModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExploreFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pDialog = Sweetalert(this, Sweetalert.PROGRESS_TYPE)
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"))
        pDialog.setTitleText("Loading")
        pDialog.setCancelable(false)
        pDialog.show()

        // Delay the dismissal of the dialog after 5 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            pDialog.dismissWithAnimation()
        }, 5000) // 5000 milliseconds = 5 seconds

        databaseReference = FirebaseDatabase.getInstance().reference


        binding.imageButton.setOnClickListener {
            finish()
        }
        retrieveTourItem()
    }

    private fun retrieveTourItem() {
        database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("foods")

        // Fetch data from the database
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear existing data before populating
                foodItems.clear()

                for (tourSnapshot in snapshot.children) {
                    val foodItem = tourSnapshot.getValue(foodModel::class.java)
                    foodItem?.let {
                        foodItems.add(it)
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
        val adapter = foodAdapter(this, foodItems, databaseReference)
        binding.fooditemsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.fooditemsRecyclerView.adapter = adapter
    }
}