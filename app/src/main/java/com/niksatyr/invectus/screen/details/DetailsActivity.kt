package com.niksatyr.invectus.screen.details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.niksatyr.invectus.R
import com.niksatyr.invectus.model.Post
import com.niksatyr.invectus.screen.base.BaseActivity
import kotlinx.android.synthetic.main.activity_details.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : BaseActivity(R.layout.activity_details) {

    private val detailsViewModel: DetailsViewModel by viewModel()

    private val postPartsAdapter = PostChildrenAdapter()

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.getDefault())

    override fun canNavigateUp() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val post: Post = intent?.extras?.getParcelable(EXTRA_POST)
            ?: throw IllegalArgumentException("This activity requires a Post object to be passed")

        postPartsPager.adapter = postPartsAdapter

        detailsViewModel.apply {
            this.post.observe(this@DetailsActivity, Observer { displayPostMetadata(it) })
            mediaUrlsWithTypes.observe(this@DetailsActivity, Observer { postPartsAdapter.setData(it) })
            state.observe(this@DetailsActivity, Observer { onStateUpdated(it) })

            setPost(post)
        }
    }

    override fun onError(reason: String) {
        super.onError(reason)
        Toast.makeText(this, R.string.failed_to_fetch_posts, Toast.LENGTH_SHORT).show()
        finish()
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