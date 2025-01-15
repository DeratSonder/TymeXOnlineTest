package com.example.currencyconverter.data.models

import com.google.gson.annotations.SerializedName

data class ExchangeResponse(
    @SerializedName("base_code")
    val baseCode: String? = null,
    @SerializedName("conversion_rates")
    val conversionRates: LinkedHashMap<String, Double>? = null,
    val documentation: String? = null,
    val result: String? = null,
    @SerializedName("terms_of_use")
    val termsOfUse: String? = null,
    @SerializedName("time_last_update_unix")
    val timeLastUpdateUnix: Int? = null,
    @SerializedName("time_last_update_utc")
    val timeLastUpdateUtc: String? = null,
    @SerializedName("time_next_update_unix")
    val timeNextUpdateUnix: Int? = null,
    @SerializedName("time_next_update_utc")
    val timeNextUpdateUtc: String? = null
)