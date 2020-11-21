package com.example.currencycalculator.data.network

import com.example.currencycalculator.data.network.model.response.FixerRatingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApi {

    @GET("latest")
    suspend fun getCurrentConversionRates(
        @Query("access_key") apiKey : String,
        @Query("symbols") symbols : String
    ): Response<FixerRatingResponse>


    companion object{
        val apiKey = "1111ba9b9deb07931e8fa9ccb6ac955e"
    }
}