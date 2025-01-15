package com.example.currencyconverter.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class NetworkConnectionObserver(
    context: Context
): ConnectivityObserver {

    private val connectActivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<ConnectivityObserver.Status> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                        trySend(ConnectivityObserver.Status.Available)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    trySend(ConnectivityObserver.Status.Unavailable)
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    trySend(ConnectivityObserver.Status.Losing)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    trySend(ConnectivityObserver.Status.Lost)
                }
            }

            connectActivityManager.registerDefaultNetworkCallback(callback)
            awaitClose {
                connectActivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }
}