package com.mdmx.dogsapplication.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ImageURLRemoteContainer(
    @SerializedName("message")
    val listOfImageURLs: List<String>,
    @SerializedName("status")
    val responseStatus: String
)