package com.iti.bago.deliveryapp.menu.current_order

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.iti.bago.deliveryapp.R
import com.iti.bago.deliveryapp.menu.pojo.ItemOrder

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

//            itemView.setOnClickListener { v: View ->
//                var position: Int = adapterPosition
//                val fragment: Fragment?
//
//                val activity = v.context as AppCompatActivity
//                fragment = CurrentOrderFragment()
//                val frgMng: FragmentManager = activity.supportFragmentManager
//                val frgTran = frgMng.beginTransaction()
//                frgTran.replace(R.id.content_frame, fragment).addToBackStack(null).commit()
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
        viewHolder.itemquan.text = item.quantity.toString()

        if (item.photo != "") {
            Glide.with(this.mcontext)
                .load(item.photo)
                .into(viewHolder.itemImg)

        }else {
            viewHolder.itemImg.visibility= View.GONE
        }
    }
}