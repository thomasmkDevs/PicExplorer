package com.pic.explorer.di

import android.content.Context
import androidx.room.Room
import com.pic.explorer.data.local.ImageDB
import com.pic.explorer.data.local.PreferencesManager
import com.pic.explorer.data.local.dao.ImageDao
import com.pic.explorer.data.remote.ApiService
import com.pic.explorer.data.repositoryImpl.ImageRepositoryImpl
import com.pic.explorer.domain.repository.ImageRepository
import com.pic.explorer.utils.NetworkUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://picsum.photos/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providePicExplorerApi(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideImageRepository(
        apiService: ApiService,
        dao: ImageDao,
        networkUtil: NetworkUtil
    ): ImageRepository = ImageRepositoryImpl(apiService, dao, networkUtil)

    @Provides
    @Singleton
    fun providePreferenceManager(@ApplicationContext context: Context): PreferencesManager =
        PreferencesManager(context)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ImageDB {
        return Room.databaseBuilder(
            context,
            ImageDB::class.java,
            "image_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideImageDao(database: ImageDB): ImageDao {
        return database.imageDao()
    }

    @Provides
    @Singleton
    fun provideNetworkChecker(@ApplicationContext context: Context): NetworkUtil {
        return NetworkUtil(context)
    }

}
