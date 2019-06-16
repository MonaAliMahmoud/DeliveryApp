package com.iti.bago.deliveryapp.menu.pojo

class Orders(
    var id: Int,
    var supermarket_name: String,
    var payment_type: String,
    var supermarket_address: String,
    var item_order: ArrayList<ItemOrder>,
    var customer: ArrayList<Customer>)