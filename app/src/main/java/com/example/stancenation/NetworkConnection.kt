@file:Suppress("DEPRECATION")

package com.example.stancenation

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import androidx.lifecycle.LiveData

class NetworkConnection(private val context: Context):LiveData<Boolean>() {
    private val connectionManager:ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private lateinit var networkConnectionCallback:ConnectivityManager.NetworkCallback
    override fun onActive() {
        super.onActive()
        updateNetworkConnection()
        connectionManager.registerDefaultNetworkCallback(connectionCallback())
    }

    override fun onInactive() {
        super.onInactive()
        connectionManager.unregisterNetworkCallback(connectionCallback())
    }
    private fun connectionCallback():ConnectivityManager.NetworkCallback{
        networkConnectionCallback = object :ConnectivityManager.NetworkCallback(){
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                postValue(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                postValue(false)
            }
        }
        return networkConnectionCallback
    }

    private fun updateNetworkConnection(){
        val networkConnection: NetworkInfo?=connectionManager.activeNetworkInfo
        postValue(networkConnection?.isConnected==true)
    }

}