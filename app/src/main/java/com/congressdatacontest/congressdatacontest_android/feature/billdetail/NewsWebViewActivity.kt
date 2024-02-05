package com.congressdatacontest.congressdatacontest_android.feature.billdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.congressdatacontest.congressdatacontest_android.databinding.ActivityNewsWebViewBinding

class NewsWebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        val url: String = intent.getStringExtra(LINK_URL).orEmpty()

        binding.billNewsWebView.webViewClient = BillDetailWebView()

        val webSet = binding.billNewsWebView.settings
        webSet.builtInZoomControls = true
        webSet.javaScriptEnabled = true

        binding.billNewsWebView.loadUrl(url)
    }

    class BillDetailWebView : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }
    }

    companion object {
        private const val LINK_URL = "link"
    }
}