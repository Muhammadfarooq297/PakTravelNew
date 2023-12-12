package com.example.paktravelnew

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paktravelnew.Adapters.roomAdapter
import com.example.paktravelnew.Adapters.vehicleAdapter
import com.example.paktravelnew.Models.RoomModel
import com.example.paktravelnew.Models.VehicleModel
import com.example.paktravelnew.databinding.ActivityExploreRoomBinding
import com.example.paktravelnew.databinding.ActivityExploreVehiclesBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import taimoor.sultani.sweetalert2.Sweetalert

class exploreVehiclesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExploreVehiclesBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private val vehicleItems: ArrayList<VehicleModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExploreVehiclesBinding.inflate(layoutInflater)
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
        binding.viewmoreVehicles.setOnClickListener {
//            val bottomSheetDialog=TourBottomSheetFragment()
//            bottomSheetDialog.show(parentFragmentManager,"TEST")
            val intent= Intent(this, viewVehiclesActivity::class.java)
            startActivity(intent)
        }
        retrieveVehicleItem()
    }
    private fun retrieveVehicleItem() {
        database = FirebaseDatabase.getInstance()
        val vehicleRef: DatabaseReference = database.reference.child("Vehicles")

        // Fetch data from the database
        vehicleRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear existing data before populating
                vehicleItems.clear()

                for (vehicleSnapshot in snapshot.children) {
                    val vehicleItem = vehicleSnapshot.getValue(VehicleModel::class.java)
                    vehicleItem?.let {
                        vehicleItems.add(it)
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
        val adapter = vehicleAdapter(this, vehicleItems, databaseReference)
        binding.vehiclesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.vehiclesRecyclerView.adapter = adapter
    }
}