package com.example.paktravelnew

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.paktravelnew.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        binding= ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val foodName=intent.getStringExtra("MenuItemName")
        val imageUri=intent.getStringExtra("MenuItemImage")
        val uri= Uri.parse(imageUri)
        binding.detailFoodName.text=foodName
        Glide.with(this@DetailsActivity).load(uri).into(binding.DetailFoodImageView)
        binding.imageButton.setOnClickListener {
            finish()
        }

        binding.buttonBook.setOnClickListener {
            val intent= Intent(this,BookingTourActivity::class.java)
            startActivity(intent)
        }
    }
}