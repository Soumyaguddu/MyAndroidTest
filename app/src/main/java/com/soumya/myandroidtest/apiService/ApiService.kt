package com.soumya.myandroidtest.apiService

import com.soumya.myandroidtest.model.ImageShowData

import com.soumya.myandroidtest.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(Constants.END_POINT)
    suspend fun getAllImages(@Query("page") page: Int, @Query("limit") limit: Int): List<ImageShowData>
}