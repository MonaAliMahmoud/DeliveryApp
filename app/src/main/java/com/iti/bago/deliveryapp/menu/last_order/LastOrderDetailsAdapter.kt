package com.iti.bago.deliveryapp.menu.last_order

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iti.bago.deliveryapp.R
import com.iti.bago.deliveryapp.menu.current_order.ItemOrderAdapter
import com.iti.bago.deliveryapp.pojo.ItemOrder
import com.iti.bago.deliveryapp.pojo.Products

class LastOrderDetailsAdapter (product: ArrayList<Products>, context: Context): RecyclerView.Adapter<LastOrderDetailsAdapter.ViewHolder>() {

    private var product: ArrayList<Products>? = product
    var mcontext: Context = context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemName: TextView
        var itemquan: TextView
        var itemImg: ImageView

        init {
            itemName = itemView.findViewById(R.id.item_name)
            itemquan = itemView.findViewById(R.id.item_quantity)
            itemImg = itemView.findViewById(R.id.item_img)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_card, viewGroup, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return product!!.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val product = product!![position]

        viewHolder.itemName.text = product.product_name
        viewHolder.itemquan.text = "${product.units_no.toString()}"+"${product.price_after}"

//        if (product.photo != "") {
//            Glide.with(this.mcontext)
//                .load(item.photo)
//                .into(viewHolder.itemImg)
//
//        }else {
//            viewHolder.itemImg.visibility= View.GONE
//        }
    }
}