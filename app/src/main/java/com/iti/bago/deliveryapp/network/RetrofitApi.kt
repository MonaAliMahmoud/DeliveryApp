package com.iti.bago.deliveryapp.network

import com.iti.bago.deliveryapp.pojo.DeliveryApi
import com.iti.bago.deliveryapp.pojo.Orders
import com.iti.bago.deliveryapp.pojo.Login
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitApi {

//    OrderDeliveryHistory/{deliver_id}
    @GET("OrderDelivery")
    fun getOrder(): Call<ArrayList<Orders>>

    @POST("delivery/login")
    fun postToken(@Body d: Login): Call<DeliveryApi>

//    @GET("OrderDeliveryHistory/{id}")
//    fun getHistory(@Path): Call<ArrayList<Orders>>


}