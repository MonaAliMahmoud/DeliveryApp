package com.iti.bago.deliveryapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private const val URL = "http://bago.ibtikar.net.sa/api/"

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

    object RetrofitManager {
        private var retrofit: Retrofit? = null
        private val retrofitInstance: Retrofit?
            get() {
                if (retrofit == null) {
                    retrofit = retrofit2.Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
                return retrofit
            }

        fun getInstance(): Retrofit? {
            return retrofitInstance
        }
    }
}