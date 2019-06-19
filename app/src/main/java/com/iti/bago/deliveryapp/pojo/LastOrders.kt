package com.iti.bago.deliveryapp.pojo

class LastOrders (
    var order_id: Int,
    var status: String,
    var payment_type: String? = null,
    var created_at: String,
    var total_price: Float,
    var supermaeket_name: String,
    var lng_src: Double,
    var lat_src: Double,
    var delivery_phone: String,
    var customer_address: String,
    var lng_dest: Double,
    var lat_dest: Double,
    var products: ArrayList<Products>)