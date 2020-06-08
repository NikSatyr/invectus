package com.niksatyr.invectus.screen.details

import androidx.lifecycle.MutableLiveData
import com.niksatyr.invectus.model.Post
import com.niksatyr.invectus.repo.InstagramRepository
import com.niksatyr.invectus.screen.base.BaseViewModel
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
                { onFailedToLoadCarouselChildren(it) }
            )

        compositeDisposable.add(disposable)
    }

    private fun onFailedToLoadCarouselChildren(throwable: Throwable) {
        Timber.e(throwable, "Failed to fetch carousel children")
        state.value = State.Error(throwable.message ?: "Unknown error")
    }

    private fun mapToMediaUrlsWithTypes(posts: List<Post>) = posts.map { Pair(it.mediaUrl, it.type) }

}