package com.example.finin.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

class ApplicationUtil {
    companion object {
        fun hasNetwork(context: Context): Boolean {
            var isConnected: Boolean = false
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnected)
                isConnected = true
            return isConnected
        }
    }
}
