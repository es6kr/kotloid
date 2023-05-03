@file:Suppress("DEPRECATION", "HasPlatformType", "unused")

package kr.es6.kotloid.webkit

import android.content.*
import android.os.*
import android.view.*
import android.webkit.CookieSyncManager
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import im.delight.android.webview.AdvancedWebView
import kr.es6.kotloid.*

open class WebFragment : Fragment(), WebClient {
	override var progressBar by lazyViewById<ProgressBar>(android.R.id.progress)
	override val url: String? by lazy { arguments?.getString(KEY_URL) }
	override val webChromeClient by lazy { BaseWebChromeClient() }
	override var webView by lazyViewById<AdvancedWebView>(R.id.webView)
	override val webViewClient by lazy { BaseWebViewClient() }

	@Deprecated("Deprecated in Java")
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		webView.onActivityResult(requestCode, resultCode, data)
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			CookieSyncManager.createInstance(context)
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
			savedInstanceState: Bundle?) = inflater.inflate(R.layout.web, container, false)!!

	override fun onDestroyView() {
		super.onDestroyView()
		progressBar = null
		webView = null
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

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) = init(savedInstanceState)

	companion object {
		const val KEY_URL = WebClient.KEY_URL
	}
}