package com.example.currencyconverter.data.repositories

import com.example.currencyconverter.data.models.ExchangeResponse
import com.example.currencyconverter.data.remote.ApiInterface
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CurrencyConverterRepository @Inject constructor(
    private val api: ApiInterface
) {

    suspend fun getExchangeRates(from: String) : Result<ExchangeResponse> {
        return try {
            val response = api.getExchangeRates(from = from)
            if (response.isSuccessful) {
                Result.success(response.body() ?: ExchangeResponse())
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}