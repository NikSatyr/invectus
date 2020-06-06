package com.niksatyr.instectus.network

import com.niksatyr.instectus.model.UserInfo
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface UserInfoService {

    @GET("me/fields=username,ig_id")
    fun getUserInfo(): Single<UserInfo>

}