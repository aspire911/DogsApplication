package com.mdmx.dogsapplication.data.repository

import com.mdmx.dogsapplication.data.remote.DogsApi
import com.mdmx.dogsapplication.data.remote.dto.ImageURLRemoteContainer
import com.mdmx.dogsapplication.domain.repository.ImageRemoteDataSource
import javax.inject.Inject

class ImageRepository @Inject constructor(
    private val api: DogsApi
) : ImageRemoteDataSource {

    override suspend fun fetch(quantity: Int): ImageURLRemoteContainer {
        return api.fetchByQuantity(quantity.toString())
    }
}
