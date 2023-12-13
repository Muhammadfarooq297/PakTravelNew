package com.example.paktravelnew

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebViewClient
import com.example.paktravelnew.databinding.ActivityTourPackagesBinding
import com.example.paktravelnew.databinding.ActivityViewVehiclesBinding

class viewVehiclesActivity : AppCompatActivity() {
    private val binding: ActivityViewVehiclesBinding by lazy{
        ActivityViewVehiclesBinding.inflate(layoutInflater)
    }
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.webView1.loadUrl("https://www.carrentpk.com/")
        binding.webView1.settings.javaScriptEnabled = true
        binding.webView1.webViewClient = WebViewClient()
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check whether the key event is the Back button and if there's history.
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.webView1.canGoBack()) {
            binding.webView1.goBack()
            return true
        }
        // If it isn't the Back button or there isn't web page history, bubble up to
        // the default system behavior. Probably exit the activity.
        return super.onKeyDown(keyCode, event)
    }

}