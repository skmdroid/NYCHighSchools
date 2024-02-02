package com.example.nychighschools.model.remote

import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getData(): Response<List<ResultItem>> {
        return apiService.getData()
    }
}