package com.pic.explorer.presentation.uiModels

import com.pic.explorer.domain.models.ImageModel

data class ImageUIState(
    val isLoading: Boolean = false,
    val images: List<ImageModel> = emptyList(),
    val filteredImages: List<ImageModel> = emptyList(),
    val authors: List<String?> = emptyList(),
    val selectedAuthor: String? = null,
    val errorMessage: String? = null,
    val selectedSort: SortType = SortType.ASCENDING
)
