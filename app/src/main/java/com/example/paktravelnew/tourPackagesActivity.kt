package com.example.paktravelnew

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebViewClient
import com.example.paktravelnew.databinding.ActivityTourPackagesBinding

class tourPackagesActivity : AppCompatActivity() {
    private val binding: ActivityTourPackagesBinding by lazy{
        ActivityTourPackagesBinding.inflate(layoutInflater)
    }
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

         binding.webView.loadUrl("https://tripventure.com.pk/")
         binding.webView.settings.javaScriptEnabled = true
         binding.webView.webViewClient = WebViewClient()


    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check whether the key event is the Back button and if there's history.
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.webView.canGoBack()) {
            binding.webView.goBack()
            return true
        }
        // If it isn't the Back button or there isn't web page history, bubble up to
        // the default system behavior. Probably exit the activity.
        return super.onKeyDown(keyCode, event)
    }
}