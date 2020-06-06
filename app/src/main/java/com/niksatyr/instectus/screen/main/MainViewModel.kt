package com.niksatyr.instectus.screen.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.niksatyr.instectus.model.Post
import com.niksatyr.instectus.model.UserInfo
import com.niksatyr.instectus.repo.InstagramRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class MainViewModel(private val instagramRepository: InstagramRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

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
        val disposable = instagramRepository.getUserMedia()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { posts.value = it.posts },
                { Timber.e(it, "Failed to fetch posts") }
            )

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}