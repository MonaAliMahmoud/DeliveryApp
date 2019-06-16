package com.iti.bago.deliveryapp.settings

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import android.view.LayoutInflater
import android.graphics.Typeface
import com.iti.bago.deliveryapp.R

class ExpandableListAdapter constructor(private val context: Context, private val titleList: List<String>, private val dataList: HashMap<String, List<String>>) : BaseExpandableListAdapter() {

    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return this.dataList[this.titleList[listPosition]]!![expandedListPosition]
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(listPosition: Int, expandedListPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView1 = convertView
        val expandedListText = getChild(listPosition, expandedListPosition) as String
        if (convertView1 == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView1 = layoutInflater.inflate(R.layout.mylist_item, null)
        }
        val expandedListTextView = convertView1!!.findViewById<TextView>(R.id.textitem)
        expandedListTextView.text = expandedListText
        return convertView1
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return this.dataList[this.titleList[listPosition]]!!.size
    }

    override fun getGroup(listPosition: Int): Any {
        return this.titleList[listPosition]
    }

    override fun getGroupCount(): Int {
        return this.titleList.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getGroupView(listPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        var convert = convertView
        val listTitle = getGroup(listPosition) as String
        if (convert == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convert = layoutInflater.inflate(R.layout.list_group, null)
        }
        val listTitleTextView = convert!!.findViewById<TextView>(R.id.textgroup)
        listTitleTextView.setTypeface(null, Typeface.BOLD)
        listTitleTextView.text = listTitle
        return convert
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }
}