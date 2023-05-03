@file:Suppress("DEPRECATION")

package kr.es6.kotloid.webkit

import android.annotation.SuppressLint
import android.content.Context
import android.os.*
import android.webkit.*
import android.widget.ProgressBar
import im.delight.android.webview.AdvancedWebView

interface WebClient {
	val progressBar: ProgressBar?
	val url: String?
	val webChromeClient: BaseWebChromeClient
	val webView: AdvancedWebView
	val webViewClient: BaseWebViewClient

	@SuppressLint("SetJavaScriptEnabled")
	fun init(savedInstanceState: Bundle?) {
		webChromeClient.progressBar = progressBar
		webView.webChromeClient = webChromeClient
		webView.webViewClient = webViewClient
		webView.run {
			settings.run {
				databaseEnabled = true
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
					databasePath = context.getDir("database", Context.MODE_PRIVATE).path
				}
				domStorageEnabled = true
				javaScriptEnabled = true
				pluginState = WebSettings.PluginState.ON
			}
			if (savedInstanceState != null) {
				restoreState(savedInstanceState)
			} else {
				loadUrl(this@WebClient.url!!)
			}
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
		}
	}

	fun addHttpHeaders(headers: Map<String, String>) {
		for (h in headers) {
			webView.addHttpHeader(h.key, h.value)
		}
	}

	fun goBack() = if (webView.canGoBack()) {
		webView.goBack()
		true
	} else false

	companion object {
		const val KEY_URL = "url"
	}
}