package com.example.currencycalculator.data.network

import com.example.currencycalculator.data.network.model.response.FixerRatingResponse
import retrofit2.Response

interface MyApiHelper {

    suspend fun getRate(
        currencyConvertingTo:String
    ): Response<FixerRatingResponse>

}