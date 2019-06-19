package com.iti.bago.deliveryapp.menu.last_order

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.iti.bago.deliveryapp.R
import com.iti.bago.deliveryapp.menu.current_order.CurrentOrderFragment
import com.iti.bago.deliveryapp.pojo.Orders

class LastOrderAdapter: RecyclerView.Adapter<LastOrderAdapter.ViewHolder>() {

    private var orders: ArrayList<Orders>? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var orderDate: TextView
        var orderTime: TextView
        var storelocation: TextView
        var customerlocation: TextView
        var status: TextView

        init {
            orderDate = itemView.findViewById(R.id.orderdate)
            orderTime = itemView.findViewById(R.id.ordertime)
            storelocation = itemView.findViewById(R.id.storelocation)
            customerlocation = itemView.findViewById(R.id.customerlocation)
            status = itemView.findViewById(R.id.status)

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
//                arguments.putString("payment", order.payment_type)
                arguments.putSerializable("itemOrder", item)
                fragment = CurrentOrderFragment()
                fragment!!.setArguments(arguments)
                val frgMng: FragmentManager = activity.supportFragmentManager
                val frgTran = frgMng.beginTransaction()
                frgTran.replace(R.id.content_frame, fragment).addToBackStack(null).commit()
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}