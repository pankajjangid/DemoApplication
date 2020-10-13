package com.pankaj.dojoin.ui.category.adapter


import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.pankaj.dojoin.R
import com.pankaj.dojoin.models.ChildPojo
import com.pankaj.dojoin.models.ParentPojo
import kotlinx.android.synthetic.main.list_group.view.*

import java.util.HashMap

class CustomExpandableListAdapter internal constructor(private val context: Context, private val titleList: List<ParentPojo>, private val dataList: HashMap<ParentPojo, List<ChildPojo>>) : BaseExpandableListAdapter() {

    override fun getChild(listPosition: Int, expandedListPosition: Int): ChildPojo {
        return this.dataList[this.titleList[listPosition]]!![expandedListPosition]
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(listPosition: Int, expandedListPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val expandedListText = getChild(listPosition, expandedListPosition)
        if (convertView == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_item, null)
        }
        val expandedListTextView = convertView!!.findViewById<TextView>(R.id.expandedListItem)
        expandedListTextView.text = expandedListText.title



        return convertView
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return this.dataList[this.titleList[listPosition]]!!.size
    }

    override fun getGroup(listPosition: Int): ParentPojo {
        return this.titleList[listPosition]
    }

    override fun getGroupCount(): Int {
        return this.titleList.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getGroupView(listPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val listTitle = getGroup(listPosition)
        if (convertView == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_group, null)
        }
        val listTitleTextView = convertView!!.listTitle
        val imgExpend = convertView!!.imgExpend
        listTitleTextView.setTypeface(null, Typeface.BOLD)
        listTitleTextView.text = listTitle.title
        if (isExpanded)
            imgExpend.setImageResource(R.drawable.ic_arrow_right)
        else
            imgExpend.setImageResource(R.drawable.ic_arrow_down)

        return convertView
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }
}
