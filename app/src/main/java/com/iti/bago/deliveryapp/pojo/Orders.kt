package com.iti.bago.deliveryapp.pojo

class Orders(
    var id: Int,
    var supermarket_name: String,
    var payment_type: String? = null,
    var supermarket_address: String,
    var supermaeket_long: Float,
    var supermaeket_lat: Float,
    var item_order: ArrayList<ItemOrder>,
    var customer: ArrayList<Customer>)