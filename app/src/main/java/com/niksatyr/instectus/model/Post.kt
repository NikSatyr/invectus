package com.niksatyr.instectus.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Post(
    @SerializedName("id") val id: String,
    @SerializedName("username") val username: String,
    @SerializedName("media_type") val type: String,
    @SerializedName("media_url") val mediaUrl: String,
    @SerializedName("caption") val caption: String?,
    @SerializedName("timestamp") val timestamp: Date,
    @SerializedName("thumbnail_url") val videoPreviewUrl: String?
) : Parcelable {

    fun isVideo() = type == TYPE_VIDEO

    fun isCarousel() = type == TYPE_CAROUSEL

    fun getPreviewUrl(): String = if (isVideo()) videoPreviewUrl!! else mediaUrl

    companion object {
        const val TYPE_IMAGE = "IMAGE"
        const val TYPE_VIDEO = "VIDEO"
        const val TYPE_CAROUSEL = "CAROUSEL_ALBUM"
    }

}