package com.example.paktravelnew.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paktravelnew.Adapters.tourAdapter
import com.example.paktravelnew.R
import com.example.paktravelnew.databinding.FragmentTourBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class TourBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding:FragmentTourBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentTourBottomSheetBinding.inflate(inflater,container,false)
        binding.buttonBack.setOnClickListener {
            dismiss()
        }
        val menuFoodNames= listOf("Hunza Valley Tour 2023-24 ","Skardu Valley Tour 2023-24"," Hunza Autumn Tour 2023-24","Hunza Cherry Blossom Tour","Hunza Valley Tour 2023-24 ","Skardu Valley Tour 2023-24"," Hunza Autumn Tour 2023-24","Hunza Cherry Blossom Tour")
        val menuItemPrice=listOf("$999","$700","$899","$900","$999","$799","$899","$999")
        val menuImage= listOf(R.drawable.hunzavalley,R.drawable.skardu,R.drawable.cherry_blossum,R.drawable.hunzavalley,R.drawable.hunzavalley,R.drawable.skardu,R.drawable.cherry_blossum,R.drawable.hunzavalley)
        val adapter= tourAdapter(ArrayList(menuFoodNames),ArrayList(menuItemPrice),ArrayList(menuImage),requireContext())
        binding.menuRecyclerView.layoutManager= LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter=adapter

        return binding.root
    }



    companion object {

    }
}