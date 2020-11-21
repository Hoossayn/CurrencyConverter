package com.example.currencycalculator.usecases

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject


@Module
@InstallIn(ActivityComponent::class)
class CurrencyConverterUseCases @Inject constructor() {

    @Provides
    fun computeConversionRate (currencyToConvertFrom: Double, currencyToConvertTo: Double): Double {
        return  currencyToConvertTo / currencyToConvertFrom
    }


}