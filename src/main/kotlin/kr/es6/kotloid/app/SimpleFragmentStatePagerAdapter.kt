package kr.es6.kotloid.app

import android.util.SparseArray
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentStatePagerAdapter as Adapter
import java.lang.ref.WeakReference

@Suppress("unused")
open class SimpleFragmentStatePagerAdapter<T> constructor(
    override val activity: FragmentActivity, override vararg val pages: Page<T>
) : Adapter(activity.supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT),
    IFragmentPagerAdapter<T> {

    override val fragments = SparseArray<WeakReference<Fragment>>()

    override fun destroyItem(container: ViewGroup, position: Int, item: Any) {
        fragments.remove(position)
        super.destroyItem(container, position, item)
    }

    override fun getCount() = super.getCount()

    override fun getItem(position: Int) = super.getItem(position)

    override fun getItemPosition(item: Any) = super<IFragmentPagerAdapter>.getItemPosition(item)

    override fun getPageTitle(position: Int) = super<IFragmentPagerAdapter>.getPageTitle(position)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        fragments.put(position, WeakReference(fragment))
        return fragment
    }
}
