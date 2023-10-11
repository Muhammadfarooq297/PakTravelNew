package com.example.paktravelnew

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.paktravelnew.databinding.ActivityRoomDetailsBinding
import com.example.paktravelnew.databinding.ActivityVehicleDetailsBinding

class vehicleDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVehicleDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityVehicleDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val vehicleType=intent.getStringExtra("VehicleType")
        val imageUri=intent.getStringExtra("VehicleImage")


        val uri= Uri.parse(imageUri)
        binding.detailRoomType.text=vehicleType
        Glide.with(this@vehicleDetailsActivity).load(uri).into(binding.DetailVehicleImageView)
        binding.imageButton.setOnClickListener {
            finish()
        }

        binding.buttonBook.setOnClickListener {
            val intent= Intent(this,BookingTourActivity::class.java)
            startActivity(intent)
        }
    }
}
