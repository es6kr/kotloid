package kr.es6.kotloid.app

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter.POSITION_NONE
import java.lang.ref.WeakReference

interface IFragmentPagerAdapter<T> {
    val activity: FragmentActivity
    val fragments: SparseArray<WeakReference<Fragment>>
    val pages: Array<out Page<T>>

    fun getCount(): Int = pages.count { it.visible }

    @Suppress("UNCHECKED_CAST", "unused")
    fun <T> getFragment(position: Int): Page<T>? = fragments.get(position)?.get() as Page<T>?

    fun getItem(position: Int) =
        getPage(position).instantiate(activity)

    fun getItemId(position: Int) = getPage(position).id

    fun getItemPosition(item: Any): Int {
        var count = 0
        for (page in pages) {
            if (page.isPageOf(item as Fragment)) {
                return if (page.visible) count else POSITION_NONE
            }
            if (page.visible) count++
        }
        return POSITION_NONE
    }

    @SuppressWarnings
    fun getPage(position: Int): Page<T> = pages.filter { it.visible }[position]

    fun getPageTitle(position: Int): CharSequence? {
        return getPage(position).title
    }
}
