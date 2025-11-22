package com.pic.explorer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pic.explorer.data.local.dao.ImageDao
import com.pic.explorer.data.local.entity.ImageEntity

@Database(entities = [ImageEntity::class], version = 1)
abstract class ImageDB: RoomDatabase() {
    abstract fun imageDao(): ImageDao
}