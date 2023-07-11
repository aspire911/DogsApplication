package com.mdmx.dogsapplication.domain.repository

import com.mdmx.dogsapplication.data.remote.dto.ImageURLRemoteContainer

interface ImageRemoteDataSource {
    suspend fun fetch(quantity: Int): ImageURLRemoteContainer
}