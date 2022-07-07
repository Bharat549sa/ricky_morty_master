package com.example.rickmortyreddit.model.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import kotlin.reflect.KMutableProperty0

class NetworkInspection(private val context: Context,
                        private var listener: KMutableProperty0<Boolean>) : ConnectivityManager.NetworkCallback() {

    private val connectivityManager: ConnectivityManager = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        context.getSystemService(ConnectivityManager::class.java)
    }else{
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    init {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(this)
        }
        else processDeprecatedLevel()
    }

    private fun processDeprecatedLevel() {
        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.O)
            listener.set(
                connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
            )

    }

    /**
     * A new Available network
     */
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        listener.set(true)
    }

    /**
     * Changing from metered to unmetered network.
     * Also changing from a slower/faster network.
     * This will also be indication of a NEW ready Network.
     */
    override fun onCapabilitiesChanged(
        network: Network,
        networkCapabilities: NetworkCapabilities
    ) {
        super.onCapabilitiesChanged(network, networkCapabilities)
    }

    /**
     * Changing from one LAN or WAN network connection.
     * Having different DNS provider/VPN connectivity.
     * This will also be indication of a NEW ready Network.
     */
    override fun onLinkPropertiesChanged(
        network: Network,
        linkProperties: LinkProperties
    ) {
        super.onLinkPropertiesChanged(network, linkProperties)
    }

    /**
     * Current network is about to disconnect or not.
     */
    override fun onLost(network: Network) {
        super.onLost(network)
        listener.set(false)
    }
}