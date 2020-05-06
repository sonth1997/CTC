package com.example.ctc.logs.view

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ctc.R
import com.example.ctc.logs.model.Logs
import com.example.ctc.utils.extension.loadImages
import kotlinx.android.synthetic.main.item_logs.view.*

class LogsAdapter (var logs : ArrayList<Logs>)
    : RecyclerView.Adapter<LogsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_logs,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return logs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(logs[position])
    }
    fun setData(list: ArrayList<Logs>){
        this.logs = list
        notifyDataSetChanged()
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(logs: Logs) {
            itemView.imgWord.loadImages("https://upload.wikimedia.org/wikipedia/commons/thumb/1/16/Facebook-icon-1.png/600px-Facebook-icon-1.png")
            itemView.tvTitleLog.text = logs.content
            itemView.tvMoneyWord.text = logs.activity.toString()
            itemView.tvDateWord.text = logs.created_at.toString()
        }
    }
}