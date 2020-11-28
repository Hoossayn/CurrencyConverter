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

