package com.example.currencycalculator.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.currencycalculator.data.repository.CurrencyRateRepository
import com.example.currencycalculator.usecases.CurrencyConverterUseCases
import com.example.currencycalculator.utils.NetworkHelper
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var networkHelper: NetworkHelper
    private lateinit var repository: CurrencyRateRepository
    private lateinit var currencyConverterUseCases: CurrencyConverterUseCases

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        networkHelper = Mockito.mock(NetworkHelper::class.java)
        repository = Mockito.mock(CurrencyRateRepository::class.java)
        currencyConverterUseCases = Mockito.mock(CurrencyConverterUseCases::class.java)
        homeViewModel = HomeViewModel(networkHelper, repository, currencyConverterUseCases)

    }

    @Test
    fun getLatestRate_bothInputGiven_returnResult() {
        homeViewModel.getLatestRates("USD", "NGN")
        val result = homeViewModel.getRatingsLiveData()
        assertThat(result).isEqualTo(380)
    }
}