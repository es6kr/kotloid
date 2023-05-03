package kr.es6.kotloid

import android.util.Base64
import android.util.Base64.*
import android.util.Log
import java.math.BigInteger
import java.util.*

fun Any.logd(msg: String) = Log.d(javaClass.simpleName, msg)
fun Any.logd(e: Throwable, msg: String? = e.localizedMessage) = Log.d(javaClass.simpleName, msg, e)
fun Any.loge(msg: String) = Log.e(javaClass.simpleName, msg)
fun Any.loge(e: Throwable, msg: String? = e.localizedMessage) = Log.e(javaClass.simpleName, msg, e)
fun Any.logi(msg: String) = Log.i(javaClass.simpleName, msg)
fun Any.logi(e: Throwable, msg: String? = e.localizedMessage) = Log.i(javaClass.simpleName, msg, e)
fun <T> T?.logi(message: T?.(T?) -> String = {it.toString()}): T? {
	message.logi(this.message(this))
	return this
}

fun Any.logv(msg: String) = Log.v(javaClass.simpleName, msg)
fun Any.logv(e: Throwable, msg: String? = e.localizedMessage) = Log.v(javaClass.simpleName, msg, e)
fun Any.logw(msg: String) = Log.w(javaClass.simpleName, msg)
fun Any.logw(e: Throwable, msg: String? = e.localizedMessage) = Log.w(javaClass.simpleName, msg, e)

val ByteArray.base64 get() = Base64.encodeToString(this,
		URL_SAFE or NO_CLOSE or NO_PADDING or NO_WRAP)!!

val Long.base64 get() = BigInteger.valueOf(this).toByteArray().base64

val UUID.base64 get() = mostSignificantBits.base64 + leastSignificantBits.base64
