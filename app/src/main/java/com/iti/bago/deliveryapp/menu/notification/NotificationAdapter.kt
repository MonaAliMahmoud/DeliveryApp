package com.iti.bago.deliveryapp.menu.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.iti.bago.deliveryapp.R
import com.iti.bago.deliveryapp.menu.current_order.CurrentOrderFragment
import com.iti.bago.deliveryapp.pojo.Orders

class NotificationAdapter(orders: ArrayList<Orders>) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>(){

    private var orders: ArrayList<Orders>? = orders

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var marketAddress: TextView
        var marketName: TextView
        var customerAddress: TextView
        var dataTime: TextView

        init {
            marketAddress = itemView.findViewById(R.id.marketAddress)
            marketName = itemView.findViewById(R.id.marketName)
            customerAddress = itemView.findViewById(R.id.customer_phone)
            dataTime = itemView.findViewById(R.id.dataTime)

            itemView.setOnClickListener { v: View  ->
                val position: Int = adapterPosition
                val fragment: Fragment?
                val activity = v.context as AppCompatActivity
                val arguments = Bundle()
                val order = orders!![position]
                val cust = order.customer[0]
                val item = order.item_order
                arguments.putString("supermarket_name", order.supermarket_name)
                arguments.putString("supermarket_address", order.supermarket_address)
                arguments.putString("customer_address", cust.address)
                arguments.putString("customer_phone_number", cust.phone_number)
                arguments.putString("customer_name", cust.name)
                arguments.putString("payment", order.payment_type)
                arguments.putSerializable("itemOrder", item)
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
            .inflate(R.layout.order_card, viewGroup, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return orders!!.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val order = orders!![position]
        val cust = order.customer[0]

        viewHolder.marketName.text = order.supermarket_name
        viewHolder.marketAddress.text = order.supermarket_address
        viewHolder.dataTime.text = order.payment_type
        viewHolder.customerAddress.text = cust.address

    }
}