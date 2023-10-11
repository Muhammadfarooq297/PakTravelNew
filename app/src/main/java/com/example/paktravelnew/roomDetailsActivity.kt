package com.example.paktravelnew

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.paktravelnew.databinding.ActivityFoodDetailsBinding
import com.example.paktravelnew.databinding.ActivityRoomDetailsBinding

class roomDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoomDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRoomDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val roomType=intent.getStringExtra("RoomType")
        val imageUri=intent.getStringExtra("RoomImage")


        val uri= Uri.parse(imageUri)
        binding.detailRoomType.text=roomType
        Glide.with(this@roomDetailsActivity).load(uri).into(binding.DetailRoomImageView)
        binding.imageButton.setOnClickListener {
            finish()
        }

        binding.buttonBook.setOnClickListener {
            val intent= Intent(this,BookingTourActivity::class.java)
            startActivity(intent)
        }
    }
}