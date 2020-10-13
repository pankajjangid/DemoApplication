package com.pankaj.dojoin.apiclient

import android.content.Context
import com.pankaj.dojoin.utils.NetworkUtils
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(
    context: Context
) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable())
            throw NoInternetException("Make sure you have an active data connection")
        return chain.proceed(chain.request())
    }

    private fun isInternetAvailable(): Boolean {
        return NetworkUtils.isNetworkConnected(applicationContext)
    }

}