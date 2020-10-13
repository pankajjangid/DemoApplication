package com.pankaj.dojoin.apiclient


import android.annotation.SuppressLint
import com.google.gson.JsonObject

import com.pankaj.dojoin.apiclient.NetworkConnectionInterceptor
import com.pankaj.dojoin.apiclient.TLSSocketFactory
import com.pankaj.dojoin.apiclient.WebServiceUrl
import com.pankaj.dojoin.network_response.CategoryResponse

import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

/**
 * Created by Pankaj Jangid
 */

interface MyApi {



    @GET(WebServiceUrl._CATEGORYS)
    suspend fun _getCategorys(): Response<CategoryResponse>


    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): MyApi {

            val interceptor = okhttp3.logging.HttpLoggingInterceptor()
            interceptor.level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY

            val tlsTocketFactory = TLSSocketFactory()

            val builder = OkHttpClient().newBuilder()
            builder.readTimeout(5, TimeUnit.MINUTES)
            builder.connectTimeout(5, TimeUnit.MINUTES)
            builder.sslSocketFactory(tlsTocketFactory, tlsTocketFactory.trustManager)
            builder.hostnameVerifier(object : HostnameVerifier {
                @SuppressLint("BadHostnameVerifier")
                override fun verify(hostname: String, session: SSLSession): Boolean {
                    return true
                }
            })

            builder.addInterceptor(networkConnectionInterceptor)
            builder.addInterceptor(interceptor)

         /*   builder.addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                    val request = chain.request().newBuilder().addHeader(
                        "token",
                        PrefUtil(MyApplication.getInstance()!!).getString(PrefUtil.KEY_AUTH_TOKEN)
                    ).addHeader(
                        "api_access_key",
                        "945]Y3x[aRJS}DxE"
                    ).addHeader(
                        "expires",
                        PrefUtil(MyApplication.getInstance()!!).getString(PrefUtil.KEY_AUTH_EXPIRE)
                    ).build()
                    return chain.proceed(request)
                }
            })*/
            val client = builder.build()

            return Retrofit.Builder()
                .client(client)
                .baseUrl(WebServiceUrl.getBaseUrl())

                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

                .build()
                .create(MyApi::class.java)
        }
    }

}
