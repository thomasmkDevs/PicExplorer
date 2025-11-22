package com.pic.explorer.data.repositoryImpl

import com.pic.explorer.data.local.dao.ImageDao
import com.pic.explorer.data.modelDto.ImageModelDTO
import com.pic.explorer.data.remote.ApiService
import com.pic.explorer.domain.models.ImageModel
import com.pic.explorer.domain.repository.ImageRepository
import com.pic.explorer.utils.NetworkUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.pic.explorer.data.local.mapper.toDomain
import com.pic.explorer.data.local.mapper.toEntity
import com.pic.explorer.data.local.mapper.toImageModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class ImageRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: ImageDao,
    private val networkUtil: NetworkUtil
) : ImageRepository {

    override fun getAllImages(): Flow<Pair<List<ImageModel>, Boolean>> = flow {
        val isConnected = networkUtil.isConnected()
        val data = dao.getAllImages().firstOrNull()?.map { it.toDomain() } ?: emptyList()

        if (!isConnected && data.isEmpty()) {
            throw Exception("NO_INTERNET_CONNECTION")
        }

        val dataToEmit = if (isConnected){
            val remoteData = apiService.getAllImages().map { it.toImageModel() }
            dao.insertImages(remoteData.map { it.toEntity() })
            remoteData
        }else{
            data
        }
        emit(dataToEmit to !isConnected)
    }
}