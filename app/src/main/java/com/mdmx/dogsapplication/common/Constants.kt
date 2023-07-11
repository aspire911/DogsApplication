package com.mdmx.dogsapplication.common

object Constants {
    const val BASE_URL = "https://dog.ceo/"
    const val DEFAULT_IMAGES_PER_REQUEST = 50
    const val FALLBACK_GENERIC_ERROR_MESSAGE = "error"

    const val CROSSFADE_DURATION = 250

    enum class ImageDownloadingLibrary {
        COIL,
        GLIDE,
        PICASSO
    }

    // We can choose what library we want to use for downloading images
    val LIBRARY = ImageDownloadingLibrary.GLIDE

}

