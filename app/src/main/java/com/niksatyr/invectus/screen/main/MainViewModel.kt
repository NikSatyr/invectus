package com.niksatyr.invectus.screen.main

import androidx.lifecycle.MutableLiveData
import com.niksatyr.invectus.model.Post
import com.niksatyr.invectus.model.UserInfo
import com.niksatyr.invectus.repo.InstagramRepository
import com.niksatyr.invectus.screen.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class MainViewModel(private val instagramRepository: InstagramRepository) : BaseViewModel() {

    val userInfo = MutableLiveData<UserInfo>()

    val posts = MutableLiveData<List<Post>>()

    fun fetchUserInfo() {
        val disposable = instagramRepository.getUserInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { userInfo.value = it },
                { Timber.e(it, "Failed to fetch user info") }
            )

        compositeDisposable.add(disposable)
    }

    fun fetchUserPosts() {
        state.value = State.Loading

        val disposable = instagramRepository.getUserMedia()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onPostsFetched(it.posts) },
                { state.value = State.Error(it.message ?: "Unknown error") }
            )

        compositeDisposable.add(disposable)
    }

    private fun onPostsFetched(posts: List<Post>) {
        state.value = State.Success
        this.posts.value = posts
    }

}