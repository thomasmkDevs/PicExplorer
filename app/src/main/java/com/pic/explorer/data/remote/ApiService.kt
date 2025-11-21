package com.pic.explorer.data.remote

import com.pic.explorer.data.modelDto.ImageModelDTO
import retrofit2.http.GET

interface ApiService {

    @GET("v2/list")
    suspend fun getAllImages(): List<ImageModelDTO>
}