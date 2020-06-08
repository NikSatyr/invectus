package com.niksatyr.invectus.dto

import com.google.gson.annotations.SerializedName
import com.niksatyr.invectus.model.Post

data class UserMediaResponse(
    @SerializedName("data") val posts: List<Post>
)