package com.pic.explorer.data.local.mapper

import com.pic.explorer.data.local.entity.ImageEntity
import com.pic.explorer.data.modelDto.ImageModelDTO
import com.pic.explorer.domain.models.ImageModel

fun ImageModelDTO.toImageModel() = ImageModel(
    id = id,
    author = author,
    imageUrl = downloadUrl
)

fun ImageEntity.toDomain(): ImageModel = ImageModel(
    id = id,
    author = author,
    imageUrl = imageUrl
)

fun ImageModel.toEntity(): ImageEntity = ImageEntity(
    id = id!!,
    author = author,
    imageUrl = imageUrl
)