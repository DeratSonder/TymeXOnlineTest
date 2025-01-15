package com.example.currencyconverter.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.models.ExchangeError
import com.example.currencyconverter.data.repositories.CurrencyConverterRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val repository: CurrencyConverterRepository
): ViewModel() {

    private val _convertedValue = MutableStateFlow<Double>(0.0)
    val convertedValue: MutableStateFlow<Double> = _convertedValue

    private val _fromCurrency = MutableStateFlow<String>("VND")
    val fromCurrency: MutableStateFlow<String> = _fromCurrency

    private val _toCurrency = MutableStateFlow<String>("USD")
    val toCurrency: MutableStateFlow<String> = _toCurrency

    private val _chosenCurrency = MutableStateFlow<String>("")
    val chosenCurrency: MutableStateFlow<String> = _chosenCurrency

    private val _error = MutableStateFlow("")
    val error: MutableStateFlow<String> = _error

    private val _isError = MutableStateFlow(false)
    val isError: MutableStateFlow<Boolean> = _isError

    fun setFromCurrency(currency: String) {
        _fromCurrency.value = currency
    }

    fun setToCurrency(currency: String) {
        _toCurrency.value = currency
    }

    fun setChosenCurrency(currency: String) {
        _chosenCurrency.value = currency
    }

    fun setErrorMessage(message: String) {
        _error.value = message
    }

    fun setIsError(isError: Boolean) {
        _isError.value = isError
    }

    fun convert(fromCurrency: String, toCurrency: String, value: Double) {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repository.getExchangeRates(fromCurrency)
            }

            if (response.isSuccess) {
                val conversionRates = response.getOrNull()?.conversionRates

                if (conversionRates != null) {
                    val rates = conversionRates.entries.find { it.key == toCurrency }?.value ?: 0.0
                    val convertedValue = value * rates
                    _convertedValue.value = convertedValue
                }

            } else {

                val errorResponse = response.exceptionOrNull()?.message
                val gson = Gson()
                val error: ExchangeError = gson.fromJson(errorResponse , ExchangeError::class.java)
                _isError.value = true
                _error.value = error.errorType

            }
        }
    }


}