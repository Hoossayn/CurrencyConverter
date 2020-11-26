package com.example.currencycalculator.data.network.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FixerRatingResponse {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null
    @SerializedName("timestamp")
    @Expose
    var timestamp: Int? = null
    @SerializedName("base")
    @Expose
    var base: String? = null
    @SerializedName("date")
    @Expose
    var date: String? = null
    @SerializedName("rates")
    @Expose
    var rates: Map<String, Double>? = null
   // var rates: Rates? = null
}

class Rates {

    @SerializedName(
        "NGN",
        alternate = ["AED", "AFN", "ARS", "AUD", "BBD", "BDT", "BGN", "BHD", "BMD", "BND", "BOB", "BRL", "BTN", "BZD", "CAD", "CHF", "CLP", "CNY", "COP", "CRC", "CZK", "DKK", "DOP", "EGP", "ETB", "EUR", "GBP", "GEL", "GHS", "GMD", "GYD", "HKD", "HRK", "HUF", "IDR", "ILS", "INR", "ISK", "JMD", "JPY", "KES", "KRW", "KWD", "KYD", "KZT", "LAK", "LKR", "LRD", "LTL", "MAD", "MDL", "MKD", "MNT", "MUR", "MWK", "MXN", "MYR", "MZN", "NAD", "NIO", "NOK", "NPR", "NZD", "OMR", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB", "SAR", "SEK", "SGD", "SOS", "SRD", "THB", "TRY", "TTD", "TWD", "TZS", "UAH", "UGX", "USD", "UYU", "VEF", "VND", "YER", "ZAR"]
    )

    @Expose
    var currentRatings: Double? = 1.00



}
