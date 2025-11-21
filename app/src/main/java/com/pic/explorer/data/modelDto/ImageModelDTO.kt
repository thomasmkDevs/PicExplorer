package com.pic.explorer.data.modelDto

import com.google.gson.annotations.SerializedName

data class ImageModelDTO(
    val id: String? = null,
    val author: String? = null,
    val width: Long? = null,
    val height: Long? = null,
    val url: String? = null,

    @SerializedName("download_url")
    val downloadUrl: String? = null
)