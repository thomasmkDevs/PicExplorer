package com.pic.explorer.domain.repository

import com.pic.explorer.domain.models.ImageModel
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    fun getAllImages(): Flow<Pair<List<ImageModel>, Boolean>>

}