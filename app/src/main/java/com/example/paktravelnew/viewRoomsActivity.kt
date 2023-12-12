package com.example.paktravelnew

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.paktravelnew.databinding.ActivityViewRoomsBinding
import com.example.paktravelnew.databinding.ActivityViewVehiclesBinding

class viewRoomsActivity : AppCompatActivity() {
    private val binding: ActivityViewRoomsBinding by lazy{
        ActivityViewRoomsBinding.inflate(layoutInflater)
    }
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.webView2.loadUrl("https://www.lamudi.pk/lahore/rooms-to-rent-1/")
        binding.webView2.settings.javaScriptEnabled = true
        binding.webView2.webViewClient = WebViewClient()
    }
}