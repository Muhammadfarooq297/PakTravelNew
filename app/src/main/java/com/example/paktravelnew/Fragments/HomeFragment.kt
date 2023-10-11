package com.example.paktravelnew.Fragments

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.paktravelnew.Adapters.tourAdapter
import com.example.paktravelnew.Models.TourModel
import com.example.paktravelnew.R
import com.example.paktravelnew.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import taimoor.sultani.sweetalert2.Sweetalert


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private val tourItems: ArrayList<TourModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentHomeBinding.inflate(layoutInflater,container,false)
        binding.viewAllTours.setOnClickListener {
            val bottomSheetDialog=TourBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager,"TEST")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val pDialog = Sweetalert(requireContext(), Sweetalert.PROGRESS_TYPE)
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"))
        pDialog.setTitleText("Loading")
        pDialog.setCancelable(false)
        pDialog.show()

        // Delay the dismissal of the dialog after 5 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            pDialog.dismissWithAnimation()
        }, 5000) // 5000 milliseconds = 5 seconds
        val imageList=ArrayList<SlideModel>()
        databaseReference = FirebaseDatabase.getInstance().reference

        imageList.add(SlideModel(R.drawable.banner4, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner5, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner6, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner7, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner8, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))

        val imageSlider=binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)
        retrieveTourItem()
//        imageSlider.setItemClickListener(object : ItemClickListener {
//            override fun doubleClick(position: Int) {
//
//            }
//
//            override fun onItemSelected(position: Int) {
//                val itemPosition=imageList[position]
//                val itemMessage="Selected Image $position"
//                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show()
//            }
//
//        })

//        val foodNames= listOf("Hunza Valley Tour 2023-24 ","Skardu Valley Tour 2023-24"," Hunza Autumn Tour 2023-24","Hunza Cherry Blossom Tour")
//        val foodPrices= listOf("$999","$700","$899","$900")
//        val foodImages= listOf(R.drawable.hunzavalley,R.drawable.skardu,R.drawable.cherry_blossum,R.drawable.hunzavalley)
//        val adapter= PopularAdapter(foodNames,foodPrices,foodImages,requireContext())
//        binding.popularRecyclerView.layoutManager=LinearLayoutManager(requireContext())
//        binding.popularRecyclerView.adapter=adapter
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
        binding.popularRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.popularRecyclerView.adapter = adapter
    }

    companion object {

    }
}