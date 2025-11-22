package com.pic.explorer

import com.pic.explorer.data.local.dao.ImageDao
import com.pic.explorer.data.local.entity.ImageEntity
import com.pic.explorer.data.modelDto.ImageModelDTO
import com.pic.explorer.data.remote.ApiService
import com.pic.explorer.data.repositoryImpl.ImageRepositoryImpl
import com.pic.explorer.utils.NetworkUtil
import kotlin.test.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import kotlin.test.assertFailsWith


class ImageRepositoryImplTest {

    lateinit var apiService: ApiService
    lateinit var dao: ImageDao
    lateinit var networkUtil: NetworkUtil
    lateinit var repository: ImageRepositoryImpl

    @Before
    fun setup(){
        apiService = mock(ApiService::class.java)
        dao = mock(ImageDao::class.java)
        networkUtil = mock(NetworkUtil::class.java)
        repository = ImageRepositoryImpl(apiService, dao, networkUtil)
    }

    @Test
    fun when_isConnected_true() = runTest{

        `when`(networkUtil.isConnected()).thenReturn(true)

        val data = listOf(
            ImageModelDTO(
                id = "1",
                author = "Thomas",
                width = 100,
                height = 100,
                url = "",
                downloadUrl = ""
            )
        )

        `when`(apiService.getAllImages()).thenReturn(data)
        `when`(dao.getAllImages()).thenReturn(flowOf(emptyList()))

        val result = repository.getAllImages().first()

        assertEquals(false, result.second)
        assertEquals(1, result.first.size)
    }

    @Test
    fun when_isConnected_false() = runTest {
        `when`(networkUtil.isConnected()).thenReturn(false)

        val offlineData = listOf(
            ImageEntity(
                id = "1",
                author = "Thomas",
                imageUrl = ""
            )
        )

        `when`(dao.getAllImages()).thenReturn(flowOf(offlineData))

        val result = repository.getAllImages().first()

        assertEquals(true, result.second)
        assertEquals(1, result.first.size)
    }

    @Test
    fun no_internet_no_cache() = runTest {
        `when`(networkUtil.isConnected()).thenReturn(false)

        `when`(dao.getAllImages()).thenReturn(flowOf(emptyList()))

        assertFailsWith<Exception> {
            repository.getAllImages().first()
        }
    }
}