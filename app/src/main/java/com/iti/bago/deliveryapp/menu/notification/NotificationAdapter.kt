package com.iti.bago.deliveryapp.menu.notification

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.iti.bago.deliveryapp.R
import com.iti.bago.deliveryapp.menu.current_order.CurrentOrderFragment
import com.iti.bago.deliveryapp.menu.pojo.Customer
import com.iti.bago.deliveryapp.menu.pojo.Orders

class NotificationAdapter(orders: ArrayList<Orders>) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>(){

    private var orders: ArrayList<Orders>? = orders
    var onItemClick: ((Orders) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var marketAddress: TextView
        var marketName: TextView
        var customerAddress: TextView
        var dataTime: TextView


        init {
            marketAddress = itemView.findViewById(R.id.marketAddress)
            marketName = itemView.findViewById(R.id.marketName)
            customerAddress = itemView.findViewById(R.id.customerAddress)
            dataTime = itemView.findViewById(R.id.dataTime)

            itemView.setOnClickListener { v: View  ->
                var position: Int = adapterPosition
//                onItemClick?.invoke(list[adapterPosition])
                val fragment: Fragment?
                val activity = v.context as AppCompatActivity
                fragment = CurrentOrderFragment()
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