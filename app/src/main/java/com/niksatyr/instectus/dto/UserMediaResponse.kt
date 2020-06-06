package com.niksatyr.instectus.dto

import com.google.gson.annotations.SerializedName
import com.niksatyr.instectus.model.Post

data class UserMediaResponse(
    @SerializedName("data") val posts: List<Post>
)