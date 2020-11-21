package com.example.currencycalculator.data.network.model

import com.example.currencycalculator.data.network.model.response.FixerRatingResponse
import retrofit2.Response

class FixerRatingsReponse(private val ratingOfCurrencyToConverTo: Response<FixerRatingResponse>,
                          private val ratingOfCurrencyToConverFrom : Response<FixerRatingResponse>) {

    fun getCurrencyToConvertFromRating(): Double {
        return getCurrencyRating(ratingOfCurrencyToConverFrom)
    }


    fun getCurrencyToConvertToRating(): Double {
        return getCurrencyRating(ratingOfCurrencyToConverTo)
    }

    private fun getCurrencyRating( response: Response<FixerRatingResponse>): Double {
        return when( response.isSuccessful){
            true -> {
                response.body()!!.rates!!.currentRatings!!
            }
            else -> {
                Double.MIN_VALUE
            }
        }

    }
}