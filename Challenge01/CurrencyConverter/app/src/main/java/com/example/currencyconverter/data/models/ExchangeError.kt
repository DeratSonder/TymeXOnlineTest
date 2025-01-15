package com.example.currencyconverter.data.models

import com.google.gson.annotations.SerializedName

data class ExchangeError(
    @SerializedName("error-type")
    val errorType: String,
    val documentation: String? = null,
    val result: String
)