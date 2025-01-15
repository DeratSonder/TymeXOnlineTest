package com.example.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.currencyconverter.navigation.CurrencyConverterNavigation
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme
import com.example.currencyconverter.utilities.ConnectivityObserver
import com.example.currencyconverter.utilities.NetworkConnectionObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        connectivityObserver = NetworkConnectionObserver(applicationContext)
        setContent {
            CurrencyConverterTheme {
                val netWorkStatus by connectivityObserver.observe().collectAsState(
                    initial = ConnectivityObserver.Status.Unavailable
                )

                CurrencyConverterNavigation(
                    netWorkStatus = netWorkStatus.toString()
                )
            }
        }
    }
}

