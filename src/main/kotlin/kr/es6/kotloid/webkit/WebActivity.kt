@file:Suppress("DEPRECATION")

package kr.es6.kotloid.webkit

import android.content.Intent
import android.os.*
import android.webkit.CookieSyncManager
import android.widget.ProgressBar
import androidx.appcompat.app.*
import im.delight.android.webview.AdvancedWebView
import kr.es6.kotloid.R

@Suppress("OverridingDeprecatedMember")
open class WebActivity : AppCompatActivity(), WebClient {
	override val progressBar: ProgressBar? by lazy { findViewById(android.R.id.progress) }
	override val url: String? by lazy { intent.getStringExtra(EXTRA_URL) }
	override val webChromeClient by lazy { BaseWebChromeClient() }
	override val webView by lazy { findViewById<AdvancedWebView>(R.id.webView)!! }
	override val webViewClient by lazy { BaseWebViewClient() }

	@Deprecated("Deprecated in Java")
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		webView.onActivityResult(requestCode, resultCode, data)
	}

	override fun onBackPressed() {
		if (!goBack()) {
			super.onBackPressed()
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.web)
		init(savedInstanceState)
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			CookieSyncManager.createInstance(this)
		}
	}

	override fun onPause() {
		super.onPause()
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			CookieSyncManager.getInstance().stopSync()
		}
	}

	override fun onResume() {
		super.onResume()
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			CookieSyncManager.getInstance().startSync()
		}
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		webView.saveState(outState)
	}

	companion object {
		const val EXTRA_URL = WebClient.KEY_URL
	}
}