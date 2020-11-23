package com.example.currencycalculator.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencycalculator.data.network.Resource
import com.example.currencycalculator.data.network.model.FixerRatingsResponse
import com.example.currencycalculator.data.network.model.response.FixerRatingResponse
import com.example.currencycalculator.data.repository.CurrencyRateRepository
import com.example.currencycalculator.usecases.CurrencyConverterUseCases
import com.example.currencycalculator.utils.NetworkHelper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Response

class HomeViewModel @ViewModelInject constructor(
        private val networkHelper: NetworkHelper,
        private val repository: CurrencyRateRepository,
        private val currencyConverterUseCases: CurrencyConverterUseCases
) : ViewModel() {

    private val currencyExchangeRateLiveData = MutableLiveData<Resource<Double>>()


    fun getLatestRates(currencyToConvertTo: String, currencyToConvertFrom: String) {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {

                val fromFlow = listOf(repository.getRates(currencyToConvertTo)).asFlow()
                val toFlow = flowOf(repository.getRates(currencyToConvertFrom))

                runBlocking {
                    fromFlow.zip(toFlow) { currencyToConvertToRating: Response<FixerRatingResponse>,
                                           currencyToConvertFromRating: Response<FixerRatingResponse> ->

                            FixerRatingsResponse(
                                    currencyToConvertToRating,
                                    currencyToConvertFromRating
                            )

                    }.collect {
                        computeConversionRate(it)
                    }
                }
            }
        }
    }

    private fun computeConversionRate(response: FixerRatingsResponse) {
        val conversionRate = currencyConverterUseCases.computeConversionRate(
                response.getCurrencyToConvertFromRating(),
                response.getCurrencyToConvertToRating()
        )
        currencyExchangeRateLiveData.value = Resource.success(
                conversionRate
        )
    }

    fun getRatingsLiveData(): MutableLiveData<Resource<Double>> {
        return currencyExchangeRateLiveData
    }
}