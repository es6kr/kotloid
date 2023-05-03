@file:Suppress("UNCHECKED_CAST", "unused")

package kr.es6.kotloid

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.*

fun Activity.showToastOnUi(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) = runOnUiThread {
    Toast.makeText(this, text, duration).show()
}

fun <T : View> Fragment.lazyViewById(id: Int) = weakRef { view!!.findViewById<T>(id) }

fun Fragment.startActivity(action: String, url: String, options: Bundle? = null) {
    ActivityCompat.startActivity(activity!!, Intent(action, Uri.parse(url)), options)
}

fun <T : Fragment> Fragment.getParent() = parentFragment as T

fun FragmentActivity.instantiateFragment(className: String): Fragment {
    return supportFragmentManager.fragmentFactory.instantiate(classLoader, className)
}
