package com.pic.explorer.presentation.viewModels

import android.media.Image
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pic.explorer.data.local.PreferencesManager
import com.pic.explorer.domain.repository.ImageRepository
import com.pic.explorer.presentation.uiModels.ImageUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _imageUiState = MutableStateFlow(ImageUIState(isLoading = true))
    val imageUiState: StateFlow<ImageUIState> = _imageUiState.asStateFlow()

    init {
        getAllImages()
    }

    fun getAllImages() {
        viewModelScope.launch {

            val selectedAuthor = preferencesManager.getSelectedAuthor()

            imageRepository.getAllImages()
                .onStart {
                    _imageUiState.update { it.copy(isLoading = true, errorMessage = null) }
                }
                .catch { e ->
                    _imageUiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Something went wrong"
                        )
                    }
                }
                .collect { result ->
                    val authors = result.map { it.author }.distinct()
                    val filtered = if (selectedAuthor.isNullOrEmpty()) result else result.filter { it.author == selectedAuthor }
                    _imageUiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            images = result,
                            authors = authors,
                            filteredImages = filtered,
                            selectedAuthor = selectedAuthor
                        )
                    }
                }
        }
    }

    fun filterByAuthor(author: String?) {

        preferencesManager.saveSelectedAuthor(author)

        if (author == null) {
            _imageUiState.update {
                it.copy(filteredImages = it.images, selectedAuthor = null)
            }
        }else{
            _imageUiState.update {
                it.copy(filteredImages = it.images.filter { it.author == author }, selectedAuthor = author)
            }
        }
    }

}