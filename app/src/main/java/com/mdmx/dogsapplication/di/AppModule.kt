package com.mdmx.dogsapplication.di

import com.mdmx.dogsapplication.data.remote.DogsApi
import com.mdmx.dogsapplication.data.repository.ImageRepository
import com.mdmx.dogsapplication.domain.repository.ImageRemoteDataSource
import com.mdmx.dogsapplication.common.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDogsApi(): DogsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideImageRepository(api: DogsApi): ImageRemoteDataSource {
        return ImageRepository(api)
    }
}