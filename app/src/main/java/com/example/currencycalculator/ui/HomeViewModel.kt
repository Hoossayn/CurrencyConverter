package com.example.currencycalculator.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencycalculator.data.network.Resource
import com.example.currencycalculator.data.network.model.FixerRatingsReponse
import com.example.currencycalculator.data.network.model.response.FixerRatingResponse
import com.example.currencycalculator.data.repository.CurrencyRateRepository
import com.example.currencycalculator.usecases.CurrencyConverterUseCases
import com.example.currencycalculator.utils.NetworkHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class HomeViewModel @ViewModelInject constructor(
    private val networkHelper: NetworkHelper,
    private val repository: CurrencyRateRepository,
    private val currencyConverterUseCases: CurrencyConverterUseCases
):ViewModel(){


    private val currencyExchangeRateLiveData = MutableLiveData<Resource<Double>>()


    fun getLatestRates(currencyToConvertTo: String, currencyToConvertFrom: String){
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()){
                val fromFlow = flowOf(repository.getRates(currencyToConvertTo))
                val toFlow = flowOf(repository.getRates(currencyToConvertFrom))
                val zipedFlow = fromFlow.zip(toFlow){ currencyToConvertToRating: Response<FixerRatingResponse>,
                                                      currencyToConvertFromRating: Response<FixerRatingResponse> -> {
                    FixerRatingsReponse(
                        currencyToConvertToRating,
                        currencyToConvertFromRating
                    )
                 }
                }

                zipedFlow.collect {
                    computeConversionRate(it.invoke())
                }
            }
        }
    }

    private fun computeConversionRate(reponse: FixerRatingsReponse) {
        val conversionRate = currencyConverterUseCases.computeConversionRate(
            reponse.getCurrencyToConvertFromRating(),
            reponse.getCurrencyToConvertToRating()
        )
        currencyExchangeRateLiveData.value = Resource.success(
            conversionRate
        )
    }

    fun getRatingsLiveData(): MutableLiveData<Resource<Double>> {
        return currencyExchangeRateLiveData
    }
}