package com.example.paktravelnew.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.paktravelnew.BookingTourActivity
import com.example.paktravelnew.R
import com.example.paktravelnew.databinding.FragmentHomeBinding
import com.example.paktravelnew.databinding.FragmentMenuBinding
import com.example.paktravelnew.exploreFoodActivity
import com.example.paktravelnew.exploreRoomActivity


class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMenuBinding.inflate(layoutInflater,container,false)
        binding.explorefoodButton.setOnClickListener {
            val intent= Intent(requireContext(),exploreFoodActivity::class.java)
            startActivity(intent)
        }
        binding.addroomButton.setOnClickListener {
            val intent= Intent(requireContext(),exploreRoomActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    companion object {

    }
}