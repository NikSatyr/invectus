package com.niksatyr.invectus.model

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("username") val username: String,
    @SerializedName("ig_id") val instagramId: String
)