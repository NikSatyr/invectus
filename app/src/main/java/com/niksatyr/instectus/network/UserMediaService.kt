package com.niksatyr.instectus.network

import com.niksatyr.instectus.dto.UserMediaResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface UserMediaService {

    @GET("me/media?fields=id,media_type,caption,media_url,timestamp,thumbnail_url,username")
    fun getUserMedia(): Single<UserMediaResponse>

}