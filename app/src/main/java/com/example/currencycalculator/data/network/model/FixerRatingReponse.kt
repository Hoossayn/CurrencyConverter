package com.example.currencycalculator.data.network.model

import com.example.currencycalculator.data.network.model.response.FixerRatingResponse
import com.example.currencycalculator.data.network.model.response.Rates
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

                response.body()?.rates?.currentRatings!!
            }
            else -> {
                Double.MIN_VALUE
            }
        }

    }
}