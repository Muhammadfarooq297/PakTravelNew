package com.example.paktravelnew.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.paktravelnew.MainActivity
import com.example.paktravelnew.R
import com.example.paktravelnew.databinding.FragmentCongratsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CongratsBottomSheetFragment : BottomSheetDialogFragment()  {
    private lateinit var binding: FragmentCongratsBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCongratsBottomSheetBinding.inflate(layoutInflater,container,false)
        binding.goHomeButton.setOnClickListener {
            val intent= Intent(requireContext(),MainActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
    companion object {

    }
}