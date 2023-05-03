package kr.es6.kotloid.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import kr.es6.kotloid.instantiateFragment
import kotlin.reflect.KClass

@Suppress("unused")
abstract class Page<T> {
    open var data: T? = null
    open var title: CharSequence? = null
    open var visible = true
    abstract val id: Long
    abstract fun isPageOf(fragment: Fragment): Boolean
    abstract fun instantiate(activity: FragmentActivity): Fragment

    open class FragmentPage<T>(fragment: KClass<out Fragment>, private val args: Bundle? = null) : Page<T>() {
        private val fragmentName: String = fragment.java.name
        override val id get() = fragmentName.hashCode().toLong()
        override fun isPageOf(fragment: Fragment) =
            fragmentName == fragment.javaClass.name

        override fun instantiate(activity: FragmentActivity) =
            activity.instantiateFragment(fragmentName).apply {
                arguments = args
            }
    }

    open class NavHostPage<T>(private val graphResId: Int, private val startDestinationArgs: Bundle? = null) : Page<T>() {
        override val id get() = graphResId.toLong()
        override fun isPageOf(fragment: Fragment) =
            fragment is NavHostFragment && fragment.id == graphResId

        override fun instantiate(activity: FragmentActivity) =
            NavHostFragment.create(graphResId, startDestinationArgs)
    }
}
