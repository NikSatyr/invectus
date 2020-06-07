package com.niksatyr.instectus.screen.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.niksatyr.instectus.model.Post
import com.niksatyr.instectus.repo.InstagramRepository

class DetailsViewModel(private val instagramRepository: InstagramRepository) : ViewModel() {

    val post = MutableLiveData<Post>()

    val mediaUrls = MutableLiveData<List<String>>()

    fun setPost(post: Post) {
        this.post.value = post
        mediaUrls.value = listOf(post.mediaUrl)
    }

}