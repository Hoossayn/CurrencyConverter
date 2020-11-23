package com.example.currencycalculator.data.network.model

import com.example.currencycalculator.data.network.model.response.FixerRatingResponse
import retrofit2.Response

class FixerRatingsResponse(private val ratingOfCurrencyToConvertTo: Response<FixerRatingResponse>,
                           private val ratingOfCurrencyToConvertFrom : Response<FixerRatingResponse>) {

    fun getCurrencyToConvertFromRating(): Double {
        return getCurrencyRating(ratingOfCurrencyToConvertFrom)
    }


    fun getCurrencyToConvertToRating(): Double {
        return getCurrencyRating(ratingOfCurrencyToConvertTo)
    }

    private fun getCurrencyRating( response: Response<FixerRatingResponse>): Double {
        return when( response.isSuccessful){
            true -> {
                println("call response ${response.body()!!.rates}")


                response.body()?.rates?.currentRatings!!
            }
            else -> {
                Double.MIN_VALUE
            }
        }

    }
}