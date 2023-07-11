package com.mdmx.dogsapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdmx.dogsapplication.common.Constants.DEFAULT_IMAGES_PER_REQUEST
import com.mdmx.dogsapplication.common.Constants.FALLBACK_GENERIC_ERROR_MESSAGE
import com.mdmx.dogsapplication.domain.repository.ImageRemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageGridViewModel @Inject constructor(
    private val imageRemoteDataSource: ImageRemoteDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(ImageGridViewState(emptyList(),null,false))
    val state: StateFlow<ImageGridViewState> = _state

    fun initialLoad(){
        loadContent()
    }

    fun onLastItemVisible() {
        if(_state.value.isLoading) return
        loadContent()
    }

    fun loadContent(quantity: Int = DEFAULT_IMAGES_PER_REQUEST) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            _state.value = runCatching {
                val result = imageRemoteDataSource.fetch(quantity)
                val listOfURLs = _state.value.listOfURLs + result.listOfImageURLs
                _state.value.copy(isLoading = false, listOfURLs = listOfURLs)
            }.getOrElse { _state.value.copy(isLoading = false, errorMessage = it.message ?: FALLBACK_GENERIC_ERROR_MESSAGE) }
        }
    }
}