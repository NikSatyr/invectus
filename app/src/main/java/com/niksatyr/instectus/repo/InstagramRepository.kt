package com.niksatyr.instectus.repo

import com.niksatyr.instectus.network.UserInfoService
import com.niksatyr.instectus.network.UserMediaService

class InstagramRepository(
    private val userMediaService: UserMediaService,
    private val userInfoService: UserInfoService
) {

    fun getUserMedia() = userMediaService.getUserMedia()

    fun getUserInfo() = userInfoService.getUserInfo()

}