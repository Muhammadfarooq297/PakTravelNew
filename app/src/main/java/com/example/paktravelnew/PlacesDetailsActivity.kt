package com.example.paktravelnew

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.paktravelnew.Fragments.CartFragment
import com.example.paktravelnew.databinding.ActivityPlacesDetailsBinding
import com.example.paktravelnew.databinding.ActivityRoomDetailsBinding

class PlacesDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlacesDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPlacesDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val roomType=intent.getStringExtra("PlaceName")
        val imageUri=intent.getStringExtra("PlaceImage")


        val uri= Uri.parse(imageUri)
        binding.detailRoomType.text=roomType
        Glide.with(this@PlacesDetailsActivity).load(uri).into(binding.DetailRoomImageView)
        binding.imageButton.setOnClickListener {
            finish()
        }

        binding.buttonBook.setOnClickListener {
            val intent= Intent(this,CartFragment::class.java)
            startActivity(intent)
        }
    }
}