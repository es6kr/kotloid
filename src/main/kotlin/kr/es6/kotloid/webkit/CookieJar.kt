@file:Suppress("DEPRECATION")

package kr.es6.kotloid.webkit

import android.net.Uri
import android.os.Build
import android.webkit.*
import kr.es6.kotlib.each
import org.apache.http.*
import org.joda.time.*
import java.net.URLEncoder

open class CookieJar(private val baseUrl: String,
		private val cookieDuration: Duration = Duration.standardDays(365)) {
	private val cookieManager by lazy { CookieManager.getInstance() }
	private val uri = Uri.parse(baseUrl)!!
	private val host = uri.host!!

	val cookie get() = cookieManager.getCookie(baseUrl) ?: ""

	val cookies get() = cookieManager.getCookie(baseUrl)?.split(";")?.map {
		it.trim()
	} ?: ArrayList()

	open fun parseCookies(response: HttpResponse) {
		setCookie(response.getHeaders("Set-Cookie"))
	}

	open fun setCookie(cookie: String) {
		cookieManager.setCookie(baseUrl, cookie)
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			CookieSyncManager.getInstance().sync()
		} else {
			cookieManager.flush()
		}
	}

	open fun setCookie(headers: Array<Header>) = headers.each {
		setCookie(value)
	}

	open fun setCookie(key: String, value: String, duration: Duration = cookieDuration) {
		setCookie("$key=${URLEncoder.encode(value)}; Path=/; Domain=.$host; Expires=" +
				DateTime().plus(duration).toDate().toGMTString())
	}
}
