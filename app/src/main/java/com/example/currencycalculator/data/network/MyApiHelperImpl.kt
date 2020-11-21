package com.example.currencycalculator.data.network

import com.example.currencycalculator.data.network.model.response.FixerRatingResponse
import retrofit2.Response
import javax.inject.Inject

class MyApiHelperImpl @Inject constructor (
    private val myApi: MyApi
):MyApiHelper{
    override suspend fun getRate(
        currencyConvertingTo: String
    ): Response<FixerRatingResponse>  = myApi.getCurrentConversionRates(MyApi.apiKey, currencyConvertingTo)

}