package com.example.paktravelnew

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.paktravelnew.Fragments.CongratsBottomSheetFragment
import com.example.paktravelnew.databinding.ActivityBookingTourBinding

class BookingTourActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookingTourBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding= ActivityBookingTourBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.placeOrderButton.setOnClickListener {
            val bottomSheetDialog=CongratsBottomSheetFragment()
            bottomSheetDialog.show(supportFragmentManager,"TEST")

        }
        binding.backButton.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}