package kr.es6.kotloid

import android.widget.TextView

val TextView.trim: String
	get() = text.toString().trim()
