package com.example.currencycalculator.usecases

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CurrencyConverterUseCasesTest {

    lateinit var currencyConverterUsecases : CurrencyConverterUseCases
    var currencyToConvertFromRate : Double = Double.MIN_VALUE
    var currencyToConvertToRate : Double = Double.MAX_VALUE


    @Before
    fun setup() {
        currencyConverterUsecases = CurrencyConverterUseCases()
        currencyToConvertFromRate = 1.107396
        currencyToConvertToRate = 401.464266

    }

    @Test
    fun getCurrencyConversionRate_shouldComputeCurrencyConvertRate_currencyConversionRate(){
        val conversionRate = currencyConverterUsecases.computeConversionRate(currencyToConvertFromRate , currencyToConvertToRate)
        Assert.assertEquals(362.53, conversionRate, 1e-3)
    }


}