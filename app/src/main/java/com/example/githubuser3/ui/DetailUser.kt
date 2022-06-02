package com.example.githubuser3.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuser3.R
import com.example.githubuser3.databinding.ActivityDetailUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUser : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        showLoadingBar(true)
        viewModel = ViewModelProvider(
            this
        )[DetailUserViewModel::class.java]
        if (username != null) {
            viewModel.setDetailUser(username)
        }
        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                showLoadingBar(false)
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvCompany.text = it.company
                    tvLocation.text = it.location
                    tvFollowers.text = StringBuilder("${it.followers} followers")
                    tvFollowing.text = StringBuilder("${it.following} following")
                    Glide.with(this@DetailUser)
                        .load(it.avatar_url)
                        .into(detailImage)
                }
            }
        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkFavUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.btnToggleFav.isChecked = true
                        _isChecked = true
                    } else {
                        binding.btnToggleFav.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.btnToggleFav.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                if (username != null) {
                    if (avatarUrl != null) {
                        viewModel.addFavUser(username, id, avatarUrl)
                        Toast.makeText(
                            applicationContext,
                            "berhasil ditambahkan ke daftar favorite",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                viewModel.removeFavUser(id)
                Toast.makeText(
                    applicationContext,
                    "berhasil dihapus dari daftar favorite",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            binding.btnToggleFav.isChecked = _isChecked
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }

    private fun showLoadingBar(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}