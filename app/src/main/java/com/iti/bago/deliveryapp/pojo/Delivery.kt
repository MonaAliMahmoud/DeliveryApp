package com.iti.bago.deliveryapp.pojo

import java.io.Serializable

class Delivery: Serializable {
    var id: Int = 0
    lateinit var name: String
    lateinit var country_code: String
    lateinit var phone_number: String
    lateinit var address: String
    lateinit var no_of_orders: String
    lateinit var status: String
    lateinit var availability: String
    lateinit var vehicle_type: String
    lateinit var plate_number: String
    lateinit var license_number: String
    var rate: Float = 0.0f
    lateinit var updated_at: String
    lateinit var created_at: String
    lateinit var api_token: String
    lateinit var new_user_flag: String
}