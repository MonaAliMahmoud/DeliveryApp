package com.iti.bago.deliveryapp.network

import com.iti.bago.deliveryapp.firebase.FireBase_Obj
import com.iti.bago.deliveryapp.pojo.DeliveryApi
import com.iti.bago.deliveryapp.pojo.LastOrders
import com.iti.bago.deliveryapp.pojo.Orders
import com.iti.bago.deliveryapp.pojo.Login
import retrofit2.Call
import retrofit2.http.*

interface RetrofitApi {

    @GET("OrderDelivery")
    fun getOrder(): Call<ArrayList<Orders>>

    @POST("delivery/login")
    fun postToken(@Body d: Login): Call<DeliveryApi>

    @GET("OrderDeliveryHistory/{id}")
    fun getHistory(@Path ("id") id: Int): Call<ArrayList<LastOrders>>

    @PUT("Deliverystatus/{id}")
    fun changeStatus(@Body @Path ("id") id: Int): Call<DeliveryApi>

    @POST("DeliveryTokenDevice")
    fun postFirebaseToken(@Body delivery_id: Int,@Body token: String): Call<FireBase_Obj>
}