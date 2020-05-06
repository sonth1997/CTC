package com.example.ctc.group.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ctc.R
import com.example.ctc.group.model.Group
import com.example.ctc.utils.extension.loadImages
import kotlinx.android.synthetic.main.item_group.view.*

class GroupAdapter (var group : ArrayList<Group>, private val mlistener: onClickItemGroup)
    : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_group,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return group.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(group[position])
        holder.itemView.setOnClickListener {
            mlistener.onClickItemMain(group[position])
        }
    }

    fun setData(list: ArrayList<Group>){
        this.group = list
        notifyDataSetChanged()
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(group: Group) {
            itemView.imgGroup.loadImages("https://upload.wikimedia.org/wikipedia/commons/thumb/1/16/Facebook-icon-1.png/600px-Facebook-icon-1.png")
            itemView.tvName.text = group.name
        }
    }
    interface onClickItemGroup {
        fun onClickItemMain(group: Group)
    }
}
