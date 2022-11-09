package com.hoon.newsarticleapp.view.web_view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import com.hoon.newsarticleapp.BaseActivity
import com.hoon.newsarticleapp.BaseViewModel
import com.hoon.newsarticleapp.databinding.ActivityWebViewBinding
import com.hoon.newsarticleapp.util.Constants.URL_HTTP

class WebViewActivity : BaseActivity<BaseViewModel, ActivityWebViewBinding>() {

    // this activity not used Viewmodel yet. (set BaseViewModel)
    override val viewModel = object : BaseViewModel() {}

    override fun getViewBinding() =
        ActivityWebViewBinding.inflate(layoutInflater)

    override fun observeData() {}

    companion object {
        const val TAG = "WebViewActivity"
        const val INTENT_KEY_URL = "URL"

        fun newIntent(context: Context, url: String) =
            Intent(context, WebViewActivity::class.java).apply {
                putExtra(INTENT_KEY_URL, url)
            }
    }

    private val articleURL by lazy { intent.getStringExtra(INTENT_KEY_URL) ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingViews()
    }

    override fun initViews(): Unit = with(binding) {
        webView.apply {
            webViewClient = CustomWebViewClient()
            webChromeClient = CustomWebChromeClient()
            settings.javaScriptEnabled = true
            loadUrl(articleURL)
        }
    }

    private fun bindingViews() = with(binding) {
        etAddressBar.setOnEditorActionListener(
            TextView.OnEditorActionListener
            { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val loadURL = v.text.toString()
                    if (URLUtil.isNetworkUrl(loadURL)) {
                        webView.loadUrl(loadURL)
                    } else {
                        webView.loadUrl(URL_HTTP + loadURL)
                    }
                }

                return@OnEditorActionListener false
            })

        layoutRefresh.setOnRefreshListener {
            webView.reload()
        }

        btnBack.setOnClickListener {
            webView.goBack()
        }

        btnForward.setOnClickListener {
            webView.goForward()
        }

        btnClose.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    inner class CustomWebViewClient : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            binding.progressBar.show()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            with(binding) {
                progressBar.hide()
                layoutRefresh.isRefreshing = false

                btnBack.isEnabled = webView.canGoBack()
                btnForward.isEnabled = webView.canGoForward()
                etAddressBar.setText(url)
            }
        }
    }

    inner class CustomWebChromeClient : WebChromeClient() {
        //current page loading progress
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            binding.progressBar.progress = newProgress
        }
    }
}