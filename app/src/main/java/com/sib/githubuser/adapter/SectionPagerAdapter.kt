package com.sib.githubuser.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sib.githubuser.R
import com.sib.githubuser.ui.FollowersFragment
import com.sib.githubuser.ui.FollowingFragment

class SectionPagerAdapter(
    private val mContext: Context,
    mFragmentManager: FragmentManager,
    data: Bundle
) : FragmentPagerAdapter(mFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentBundle: Bundle

    init {
        fragmentBundle = data
    }

    @StringRes
    private val TAB_TITLE = intArrayOf(R.string.tab_1, R.string.tab_2)

    override fun getCount(): Int = 2


    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mContext.resources.getString(TAB_TITLE[position])
    }

}