package com.niksatyr.instectus.screen.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.niksatyr.instectus.R
import com.niksatyr.instectus.model.Post
import kotlinx.android.synthetic.main.activity_details.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : AppCompatActivity(R.layout.activity_details) {

    private val detailsViewModel: DetailsViewModel by viewModel()

    private val postPartsAdapter = PostPartsAdapter()

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBar()

        val post: Post = intent?.extras?.getParcelable(EXTRA_POST)
            ?: throw IllegalArgumentException("This activity requires a Post object to be passed")

        postPartsPager.adapter = postPartsAdapter

        detailsViewModel.post.observe(this, Observer {
            displayPostMetadata(it)
        })

        detailsViewModel.mediaUrls.observe(this, Observer {
            postPartsAdapter.setData(it)
        })

        detailsViewModel.setPost(post)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun displayPostMetadata(it: Post) {
        it.caption?.let {
            tvCaption.visibility = View.VISIBLE
            tvCaption.text = it
        }
        tvUsername.text = it.username
        tvPublishDate.text = dateFormat.format(it.timestamp)
    }

    companion object {
        const val EXTRA_POST = "EXTRA_POST"
    }

}