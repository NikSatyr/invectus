package com.niksatyr.instectus.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Post(
    @SerializedName("id") val id: String,
    @SerializedName("username") val username: String,
    @SerializedName("media_type") val mediaType: String,
    @SerializedName("media_url") val mediaUrl: String,
    @SerializedName("caption") val caption: String,
    @SerializedName("timestamp") val timestamp: Date,
    @SerializedName("thumbnail_url") val videoPreviewUrl: String?
) {

    fun isVideo() = mediaType == TYPE_VIDEO

    companion object {
        const val TYPE_IMAGE = "MEDIA"
        const val TYPE_VIDEO = "VIDEO"
    }

}