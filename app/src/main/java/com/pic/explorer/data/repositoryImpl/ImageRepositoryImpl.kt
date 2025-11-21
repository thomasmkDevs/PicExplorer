package com.pic.explorer.data.repositoryImpl

import com.pic.explorer.data.modelDto.ImageModelDTO
import com.pic.explorer.data.remote.ApiService
import com.pic.explorer.domain.models.ImageModel
import com.pic.explorer.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ImageRepository {

    override fun getAllImages(): Flow<List<ImageModel>> = flow {
        emit(apiService.getAllImages().map { it.toImageModel() })
    }
}

fun ImageModelDTO.toImageModel() = ImageModel(
    id = id,
    author = author,
    imageUrl = downloadUrl
)