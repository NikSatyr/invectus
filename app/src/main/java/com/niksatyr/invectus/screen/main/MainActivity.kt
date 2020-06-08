package com.niksatyr.invectus.screen.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.niksatyr.invectus.R
import com.niksatyr.invectus.model.Post
import com.niksatyr.invectus.screen.base.BaseActivity
import com.niksatyr.invectus.screen.details.DetailsActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(R.layout.activity_main) {

    private val mainViewModel: MainViewModel by viewModel()

    private val postsAdapter = PostsAdapter { openPostDetails(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btnRetryFetchPosts.setOnClickListener { onDataUpdateRequired() }

        rvPosts.apply {
            layoutManager = GridLayoutManager(this@MainActivity, COLUMNS_COUNT)
            adapter = postsAdapter
        }

        mainViewModel.apply {
            userInfo.observe(this@MainActivity, Observer { supportActionBar?.title = it.username })
            posts.observe(this@MainActivity, Observer { postsAdapter.setData(it) })
            state.observe(this@MainActivity, Observer { onStateUpdated(it) })
        }

        if (savedInstanceState == null) {
            onDataUpdateRequired()
        }
    }

    private fun openPostDetails(post: Post) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.EXTRA_POST, post)
        startActivity(intent)
    }

    private fun onDataUpdateRequired() {
        mainViewModel.fetchUserInfo()
        mainViewModel.fetchUserPosts()
    }

    override fun onSuccess() {
        super.onSuccess()

        rvPosts.visibility = View.VISIBLE
        progressBarPostsLoading.visibility = View.INVISIBLE
        btnRetryFetchPosts.visibility = View.INVISIBLE
    }

    override fun onError(reason: String) {
        super.onError(reason)

        rvPosts.visibility = View.VISIBLE
        progressBarPostsLoading.visibility = View.INVISIBLE
        Toast.makeText(this, getString(R.string.failed_to_fetch_posts), Toast.LENGTH_SHORT).show()
        btnRetryFetchPosts.visibility = View.VISIBLE
    }

    override fun onLoading() {
        super.onLoading()

        rvPosts.visibility = View.INVISIBLE
        progressBarPostsLoading.visibility = View.VISIBLE
        btnRetryFetchPosts.visibility = View.INVISIBLE
    }

    companion object {
        private const val COLUMNS_COUNT = 3
    }

}