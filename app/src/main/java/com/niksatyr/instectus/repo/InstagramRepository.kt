package com.niksatyr.instectus.repo

import com.niksatyr.instectus.dto.UserMediaResponse
import com.niksatyr.instectus.model.Post
import com.niksatyr.instectus.network.UserInfoService
import com.niksatyr.instectus.network.UserMediaService
import io.reactivex.rxjava3.core.Single

class InstagramRepository(
    private val userMediaService: UserMediaService,
    private val userInfoService: UserInfoService
) {

    fun getUserMedia() = userMediaService.getUserMedia()

    fun getUserInfo() = userInfoService.getUserInfo()

    fun getCarouselChildren(post: Post): Single<UserMediaResponse> {
        if (!post.isCarousel()) {
            throw IllegalArgumentException("Provided post is not a type of carousel")
        }

        return userMediaService.getCarouselChildren(post.id)
    }

}