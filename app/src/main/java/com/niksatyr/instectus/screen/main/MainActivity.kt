package com.niksatyr.instectus.screen.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.niksatyr.instectus.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val mainViewModel: MainViewModel by viewModel()

    private val postsAdapter = PostsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rvPosts.apply {
            layoutManager = GridLayoutManager(this@MainActivity, COLUMNS_COUNT)
            adapter = postsAdapter
        }

        mainViewModel.userInfo.observe(this, Observer {
            supportActionBar?.title = it.username
        })

        mainViewModel.posts.observe(this, Observer {
            postsAdapter.setData(it)
        })

        mainViewModel.fetchUserInfo()
        mainViewModel.fetchUserPosts()
    }

    companion object {
        private const val COLUMNS_COUNT = 3
    }

}