package com.niksatyr.instectus.screen.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.niksatyr.instectus.model.Post
import com.niksatyr.instectus.repo.InstagramRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class DetailsViewModel(private val instagramRepository: InstagramRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val post = MutableLiveData<Post>()

    val mediaUrls = MutableLiveData<List<Pair<String, String>>>()

    fun setPost(post: Post) {
        this.post.value = post
        fetchCarouselChildrenIfRequired()
    }

    private fun fetchCarouselChildrenIfRequired() {
        post.value?.let {
            if (it.isCarousel()) {
                fetchCarouselChildren(it)
            } else {
                mediaUrls.value = listOf(Pair(it.mediaUrl, it.type))
            }
        }
    }

    private fun fetchCarouselChildren(post: Post) {
        val disposable = instagramRepository.getCarouselParts(post)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { mapToUrlsWithTypes(it.posts) }
            .subscribe(
                { mediaUrls.value = it },
                { Timber.e(it, "Failed to fetch post children") }
            )

        compositeDisposable.add(disposable)
    }

    private fun mapToUrlsWithTypes(posts: List<Post>) = posts.map { Pair(it.mediaUrl, it.type) }

}