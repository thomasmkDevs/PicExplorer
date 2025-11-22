package com.pic.explorer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images_data")
data class ImageEntity (
    @PrimaryKey val id: String,
    val author: String?,
    val imageUrl: String?
)