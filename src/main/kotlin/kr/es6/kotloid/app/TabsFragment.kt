package kr.es6.kotloid.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kr.es6.kotloid.R
import kr.es6.kotloid.lazyViewById

@Suppress("HasPlatformType", "MemberVisibilityCanBePrivate", "unused")
open class TabsFragment<T>(contentLayoutId: Int = R.layout.fragment_tabs) : Fragment(contentLayoutId)
    where T : PagerAdapter, T : IFragmentPagerAdapter<TabsFragment.Tab> {
    var tabLayout by lazyViewById<TabLayout>(R.id.tabLayout)
    var viewPager by lazyViewById<ViewPager>(R.id.viewPager)

    var adapter: T? = null
        set(value) {
            field = value
            viewPager.adapter = value
            val tabLayout = tabLayout
            tabLayout.setupWithViewPager(viewPager)
            for (i in 0 until tabLayout.tabCount) {
                value!!.getPage(i).data?.icon?.let(tabLayout.getTabAt(i)!!::setIcon)
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        tabLayout = null
        viewPager = null
    }

    open class Tab constructor(
        var icon: Int? = null
    )

    companion object {
        inline fun <reified T : Fragment> tabPage(
            title: CharSequence? = null,
            icon: Int? = null,
            args: Bundle? = null
        ) = Page.FragmentPage<Tab>(T::class, args).apply {
            data = Tab(icon)
            this.title = title
        }
    }
}
