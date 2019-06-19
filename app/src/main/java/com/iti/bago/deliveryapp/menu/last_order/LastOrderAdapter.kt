package com.iti.bago.deliveryapp.menu.last_order

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.iti.bago.deliveryapp.R
import com.iti.bago.deliveryapp.menu.current_order.CurrentOrderFragment
import com.iti.bago.deliveryapp.pojo.LastOrders
import com.iti.bago.deliveryapp.pojo.Orders
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.collections.ArrayList

class LastOrderAdapter(lastOrders: ArrayList<LastOrders>, context: Context): RecyclerView.Adapter<LastOrderAdapter.ViewHolder>() {

    private var lorders: ArrayList<LastOrders>? = lastOrders
    var mcontext: Context = context

    private var geocoder: Geocoder? = null
    private var latitude: Double = 0.toDouble()
    private var longitude: Double = 0.toDouble()
    var Address: String = ""
    var city: String = ""
    var addresses: List<Address>? = null

    var storeAddress: String = ""

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var orderDate: TextView
        var storelocation: TextView
        var customerlocation: TextView
        var status: TextView

        init {
            orderDate = itemView.findViewById(R.id.orderdate)
            storelocation = itemView.findViewById(R.id.storelocation)
            customerlocation = itemView.findViewById(R.id.customerlocation)
            status = itemView.findViewById(R.id.status)

            itemView.setOnClickListener { v: View  ->
                val position: Int = adapterPosition
                val fragment: Fragment?
                val activity = v.context as AppCompatActivity
                val arguments = Bundle()
                val order = lorders!![position]
                val product = order.products
                arguments.putString("store_name", order.supermaeket_name)
                arguments.putString("store_address", storeAddress)
                arguments.putString("payment_type", order.payment_type)
                arguments.putString("customer_address", order.customer_address)
                arguments.putSerializable("product", product)
                fragment = CurrentOrderFragment()
                fragment.setArguments(arguments)
                val frgMng: FragmentManager = activity.supportFragmentManager
                val frgTran = frgMng.beginTransaction()
                frgTran.replace(R.id.content_frame, fragment).addToBackStack(null).commit()
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.last_order_card, viewGroup, false)
        return ViewHolder(v)    }

    override fun getItemCount(): Int {
        return lorders!!.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val order = lorders!![position]

        latitude = order.lat_src
        longitude = order.lng_src
        geocoder = Geocoder(mcontext, Locale.getDefault())
        addresses = geocoder!!.getFromLocation(latitude, longitude, 1)
        city = addresses!![0].locality
        val state = addresses!![0].adminArea
        val country = addresses!![0].countryName
        val postalCode = addresses!![0].postalCode
        Log.i("geocoders ", Address + city + state + country + postalCode)
        storeAddress = "$Address"+"$city"+"$state"+"$country"
        viewHolder.orderDate.text = order.created_at
        viewHolder.storelocation.text = "$Address"+"$city"+"$state"+"$country"
        viewHolder.customerlocation.text = order.customer_address
        viewHolder.status.text = order.status
    }
}