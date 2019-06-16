package com.iti.bago.deliveryapp.menu

import com.iti.bago.deliveryapp.menu.pojo.Orders
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitApi {

    @GET("api/OrderDelivery")
    fun getOrder(): Call<ArrayList<Orders>>
}