@file:Suppress("DEPRECATION", "OverridingDeprecatedMember")

package kr.es6.kotloid.webkit

import android.content.Intent
import android.net.Uri
import android.net.UrlQuerySanitizer
import android.os.Build
import android.provider.Browser
import android.webkit.*
import androidx.annotation.RequiresApi
import kr.es6.kotloid.installPackage
import kr.es6.kotloid.startActivity

open class BaseWebViewClient : WebViewClient() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest) = loadUri(view, request.url)

    @Deprecated("Deprecated in Java")
    override fun shouldOverrideUrlLoading(view: WebView, url: String) = loadUri(view, Uri.parse(url))

    private fun loadUri(view: WebView, uri: Uri) = try {
        val context = view.context
        val url = uri.toString()
        when (uri.scheme) {
            "file" -> false
            "http", "https" -> {
                if (url.startsWith("https://m.facebook.com/v2.0/dialog/oauth?redirect")) {
                    view.loadUrl(UrlQuerySanitizer(url).getValue("redirect"))
                    true
                } else false
            }

            "intent" -> context.run {
                val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                if (uri.host == "scan") {
                    startActivity(intent)
                    return@run true
                }
                val pkg = intent.`package`!!
                if (packageManager.getLaunchIntentForPackage(pkg) != null) {
                    startActivity(intent)
                } else {
                    installPackage(pkg, intent.getStringExtra("referrer") ?: "")
                }
                true
            }

            "mailto", "sms" -> {
                context.startActivity(Intent.ACTION_SENDTO, url)
                true
            }

            "market" -> {
                Intent.parseUri(url, Intent.URI_INTENT_SCHEME).apply {
                    context.startActivity(this)
                }
                true
            }

            "tel" -> {
                context.startActivity(Intent.ACTION_DIAL, url)
                true
            }

            else -> {
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.packageName)
                context.startActivity(intent)
                true
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

    override fun onPageFinished(view: WebView, url: String) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.getInstance().sync()
        } else {
            CookieManager.getInstance().flush()
        }
    }
}