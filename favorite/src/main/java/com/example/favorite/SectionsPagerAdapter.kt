@file:Suppress("DEPRECATION")

package com.example.favorite

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.favorite.movie.MovieFavFragment
import com.example.favorite.tv.TvFavFragment

@Suppress("DEPRECATION")
class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
                R.string.movie_fav,
                R.string.tv_show_fav)
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MovieFavFragment()
            1 -> TvFavFragment()
            else -> MovieFavFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int = TAB_TITLES.size
}