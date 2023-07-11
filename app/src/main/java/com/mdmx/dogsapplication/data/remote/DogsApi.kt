package com.mdmx.dogsapplication.data.remote

import com.mdmx.dogsapplication.data.remote.dto.ImageURLRemoteContainer
import retrofit2.http.GET
import retrofit2.http.Path

interface DogsApi {
    @GET("/api/breeds/image/random/{quantity}")
    suspend fun fetchByQuantity(@Path("quantity") quantity: String): ImageURLRemoteContainer
}