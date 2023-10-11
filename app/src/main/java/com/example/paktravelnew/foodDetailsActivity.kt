package com.example.paktravelnew

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.paktravelnew.databinding.ActivityDetailsBinding
import com.example.paktravelnew.databinding.ActivityFoodDetailsBinding

class foodDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFoodDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val foodName=intent.getStringExtra("MenuItemName")
        val imageUri=intent.getStringExtra("MenuItemImage")
        val uri= Uri.parse(imageUri)
        binding.detailFoodName.text=foodName
        Glide.with(this@foodDetailsActivity).load(uri).into(binding.DetailFoodImageView)
        binding.imageButton.setOnClickListener {
            finish()
        }

        binding.buttonBook.setOnClickListener {
            val intent= Intent(this,BookingTourActivity::class.java)
            startActivity(intent)
        }
    }
}