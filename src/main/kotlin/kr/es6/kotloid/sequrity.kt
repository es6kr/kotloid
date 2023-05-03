package kr.es6.kotloid

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

@Throws(NoSuchAlgorithmException::class)
fun md5Encoding(inputValue: ByteArray): String {
	val md5 = MessageDigest.getInstance("MD5")
	val builder = StringBuilder()
	for (b in md5.digest(inputValue)) {
		builder.append(String.format("%02x", b))
	}
	return builder.toString()
}

val String.md5 get() = md5Encoding(toByteArray())
