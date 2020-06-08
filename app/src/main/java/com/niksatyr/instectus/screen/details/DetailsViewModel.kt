package com.niksatyr.instectus.screen.details

import androidx.lifecycle.MutableLiveData
import com.niksatyr.instectus.model.Post
import com.niksatyr.instectus.repo.InstagramRepository
import com.niksatyr.instectus.screen.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class DetailsViewModel(private val instagramRepository: InstagramRepository) : BaseViewModel() {

    val post = MutableLiveData<Post>()

    val mediaUrlsWithTypes = MutableLiveData<List<Pair<String, String>>>()

    fun setPost(post: Post) {
        this.post.value = post
        fetchMediaUrlsWithTypes()
    }

    private fun fetchMediaUrlsWithTypes() {
        post.value?.let {
            if (it.isCarousel()) {
                fetchCarouselChildren(it)
            } else {
                mediaUrlsWithTypes.value = listOf(Pair(it.mediaUrl, it.type))
            }
        }
    }

    private fun fetchCarouselChildren(post: Post) {
        val disposable = instagramRepository.getCarouselChildren(post)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { mapToMediaUrlsWithTypes(it.posts) }
            .subscribe(
                { mediaUrlsWithTypes.value = it },
                { Timber.e(it, "Failed to fetch carousel children") }
            )

        compositeDisposable.add(disposable)
    }

    private fun mapToMediaUrlsWithTypes(posts: List<Post>) = posts.map { Pair(it.mediaUrl, it.type) }

}