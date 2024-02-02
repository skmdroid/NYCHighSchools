package com.example.nychighschools

import com.example.nychighschools.model.remote.ApiService
import com.example.nychighschools.model.remote.Repository
import com.example.nychighschools.model.remote.ResultItem
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RepositoryTest {

    @MockK
    private lateinit var mockApiService: ApiService
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = Repository(mockApiService)
    }

    @Test
    fun `fetchData success`() = runBlocking {
        // Act
        val result = repository.getData()

        // Assert
        assertEquals(true, result.isSuccessful)
    }

    @Test
    fun `fetchData failure`() = runBlocking {
        // Mock a failure response
        val errorCode = 404
        coEvery { mockApiService.getData() } returns Response.error(errorCode, ResponseBody.create(null, "Not Found"))

        // Act
        val result = repository.getData()

        // Assert
        assertEquals(false, result.isSuccessful)
    }

    @Test(expected = RuntimeException::class)
    fun `fetchData exception`() = runBlocking {
        // Mock an exception
        coEvery { mockApiService.getData() } throws RuntimeException("Network error")

        // Act
        repository.getData()
    }
}
