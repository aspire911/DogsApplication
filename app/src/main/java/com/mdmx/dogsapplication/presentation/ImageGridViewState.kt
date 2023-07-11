package com.mdmx.dogsapplication.presentation

data class ImageGridViewState(
    val listOfURLs: List<String>,
    val errorMessage: String?,
    val isLoading: Boolean = false
)

