package com.example.paktravelnew

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.paktravelnew.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {
    private val binding:ActivityLogInBinding by lazy {
        ActivityLogInBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.donthaveButton.setOnClickListener {
            val intent= Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            val intent= Intent(this,ChooseLocationActivity::class.java)
            startActivity(intent)
        }

    }
}