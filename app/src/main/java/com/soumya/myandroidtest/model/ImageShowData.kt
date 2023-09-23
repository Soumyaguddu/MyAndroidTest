package com.soumya.myandroidtest.model

import com.squareup.moshi.Json

data class ImageShowData(
    @Json(name= "author")
    val author: String,
    @Json(name= "download_url")
    val download_url: String,
    @Json(name= "height")
    val height: Int,
    @Json(name= "id")
    val id: String,
    @Json(name= "url")
    val url: String,
    @Json(name= "width")
    val width: Int
)