package com.iti.bago.deliveryapp.menu.current_order

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.iti.bago.deliveryapp.R
import com.iti.bago.deliveryapp.pojo.ItemOrder

class ItemOrderAdapter(items: ArrayList<ItemOrder>, context: Context): RecyclerView.Adapter<ItemOrderAdapter.ViewHolder>() {

    private var items: ArrayList<ItemOrder>? = items
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
        return items!!.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items!![position]

        viewHolder.itemName.text = item.name
        viewHolder.itemquan.text = item.quantity.toString()+ item.price_after

        if (item.photo != "") {
            Glide.with(this.mcontext)
                .load(item.photo)
                .into(viewHolder.itemImg)

        }else {
            viewHolder.itemImg.visibility= View.GONE
        }
    }
}