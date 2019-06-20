package com.iti.bago.deliveryapp

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import com.iti.bago.deliveryapp.pojo.DeliveryApi
import com.google.gson.Gson

class SharedPref {

    fun saveDeliveryObj(deliveryApi: DeliveryApi, context: Context)
    {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val prefsEditor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(deliveryApi)
        Log.i("Obj", json.toString())
        prefsEditor.putString("DeliveryObject", json)
        prefsEditor.apply()
    }

    fun getDeliveryObj(context: Context): DeliveryApi?{
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val gson = Gson()
        val json = prefs.getString("DeliveryObject", "")
        var obj = gson.fromJson(json, DeliveryApi::class.java)
        if (json!=null) {
            obj = gson.fromJson<DeliveryApi>(json, DeliveryApi::class.java)
        }
        return obj
    }

    fun setFirebaseToken(token :String?, context: Context) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val prefsEditor = prefs.edit()
        prefsEditor.putString("firebaseToken", token!!)
        prefsEditor.apply()
    }

    fun getFirebaseToken(context: Context): String?{
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val token = prefs.getString("firebaseToken", "")
        return token
    }

    fun clear(context: Context) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val prefsEditor = prefs.edit()
        prefsEditor.clear()
        prefsEditor.apply()
    }
}