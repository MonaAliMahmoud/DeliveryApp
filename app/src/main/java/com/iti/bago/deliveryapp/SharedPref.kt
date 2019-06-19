package com.iti.bago.deliveryapp

import android.content.Context
import android.preference.PreferenceManager
import com.iti.bago.deliveryapp.pojo.DeliveryApi
import com.google.gson.Gson


class SharedPref {

    companion object{
        var SHARED_KEY = "Bago"
    }

    fun saveDeliveryObj(deliveryApi: DeliveryApi, context: Context)
    {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val prefsEditor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(deliveryApi)
        prefsEditor.putString("DeliveryObject", json)
        prefsEditor.apply()
    }

    fun getDeliveryObj(context: Context): DeliveryApi?{
        val prefs = context.getSharedPreferences(SHARED_KEY,Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString("DeliveryObject", null)
        if (json!=null) {
            val obj = gson.fromJson<DeliveryApi>(json, DeliveryApi::class.java)
            return obj
        }
        return null
    }

    fun setFirebaseToken(token :String?, context: Context) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val prefsEditor = prefs.edit()
        prefsEditor.putString("firebaseToken", token!!)
        prefsEditor.apply()
    }

    fun clear(context: Context) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val prefsEditor = prefs.edit()
        prefsEditor.clear()
        prefsEditor.apply()
    }

}