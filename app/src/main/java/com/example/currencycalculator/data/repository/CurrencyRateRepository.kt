package com.example.currencycalculator.data.repository

import com.example.currencycalculator.data.network.MyApi
import com.example.currencycalculator.data.network.MyApiHelper
import javax.inject.Inject

class CurrencyRateRepository @Inject constructor(private val apiHelper: MyApiHelper){

    suspend fun getRates(currencyConvertingTo:String) = apiHelper.getRate(currencyConvertingTo)

}