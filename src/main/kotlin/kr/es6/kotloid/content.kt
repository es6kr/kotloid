package kr.es6.kotloid

import android.content.Intent
import android.net.Uri

var Intent.url: String?
	get() = data?.toString()
	set(value) {
		data = Uri.parse(value)
	}
