package com.example.currencyconverter.data.remote

import com.example.currencyconverter.data.models.ExchangeResponse
import com.example.currencyconverter.utilities.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("{key}/latest/{from}")
    suspend fun getExchangeRates(
        @Path("key") key: String = API_KEY,
        @Path("from") from: String,
    ): Response<ExchangeResponse>

}