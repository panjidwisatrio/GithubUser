package com.panjidwisatrio.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.panjidwisatrio.githubuser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private var _binding : ActivityDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarDetail.topAppBarDetail)
        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setViewPager()
        setData()
    }

    private fun setData() {
        val dataUser = intent.getParcelableExtra<UserData>(EXTRA_DATA) as UserData

        with(binding) {
            tvUsername.text = dataUser.username

            if (dataUser.name.equals("null")) {
                tvName.text = ""
            } else {
                tvName.text = dataUser.name
            }
            if (dataUser.company.equals("null")) {
                tvCompany.text = getString(R.string.not_added_company)
            } else {
                tvCompany.text = dataUser.company
            }
            if (dataUser.location.equals("null")) {
                tvLocation.text = getString(R.string.not_added_location)
            } else {
                tvLocation.text = dataUser.location
            }

            followerDetail.text = dataUser.followers
            followingDetail.text = dataUser.following
            repoDetail.text = dataUser.repository
            Glide.with(this@DetailActivity)
                .load(dataUser.avatar)
                .into(ivAvatar)
        }
    }

    private fun setViewPager() {
        val bindingTab = binding.tabs
        val bindingViewPager = binding.container
        val sectionTabAdapter = SectionTabAdapter(this)

        bindingViewPager.adapter = sectionTabAdapter

        TabLayoutMediator(bindingTab, bindingViewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val dataUser = intent.getParcelableExtra<UserData>(EXTRA_DATA) as UserData
        when(item.itemId) {
            R.id.share -> {
                val share = Intent(Intent.ACTION_SEND)
                share.type = "text/plain"
                share.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL")
                share.putExtra(Intent.EXTRA_TEXT, dataUser.link)
                startActivity(Intent.createChooser(share, "Share URL"))
            }
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.tab1,
            R.string.tab2
        )

        const val EXTRA_DATA = "extra_data"
    }
}