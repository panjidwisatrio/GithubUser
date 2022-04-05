package com.panjidwisatrio.githubuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionTabAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = when (position){
            0 -> FollowersFragment()
            1 -> FollowingFragment()
            else -> Fragment()
        }

        fragment.arguments = Bundle().apply {
            putInt("section_number", position + 1)
        }
        return fragment
    }
}