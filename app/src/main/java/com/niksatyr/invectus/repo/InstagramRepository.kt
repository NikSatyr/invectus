package com.niksatyr.invectus.repo

import com.niksatyr.invectus.dto.UserMediaResponse
import com.niksatyr.invectus.model.Post
import com.niksatyr.invectus.network.UserInfoService
import com.niksatyr.invectus.network.UserMediaService
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