package com.iti.bago.deliveryapp.menu

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceBuilder {
    // Before release, change this URL to your live server URL such as "https://smartherd.com/"
    private const val URL = "http://bago.ibtikar.net.sa/"

//    val headerInterceptor = object: Interceptor {
//
//        override fun intercept(chain: Interceptor.Chain): Response {
//
//            var request = chain.request()
//
//            request = request.newBuilder()
//                .addHeader("x-device-type", Build.DEVICE)
//                .addHeader("Accept-Language", Locale.getDefault().language)
//                .build()
//
//            val response = chain.proceed(request)
//            return response
//        }
//    }

    // Create OkHttp Client
    private val okHttp = OkHttpClient.Builder()
        .callTimeout(5, TimeUnit.SECONDS)

    // Create Retrofit Builder
    private val builder = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    // Create Retrofit Instance
    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }

}