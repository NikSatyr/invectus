package com.niksatyr.invectus.network

import com.niksatyr.invectus.model.UserInfo
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface UserInfoService {

    @GET("me/?fields=username,ig_id")
    fun getUserInfo(): Single<UserInfo>

}