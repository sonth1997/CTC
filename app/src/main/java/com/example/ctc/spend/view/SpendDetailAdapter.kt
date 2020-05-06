package com.example.ctc.spend.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ctc.R
import com.example.ctc.spend.model.SpendDetail
import com.example.ctc.utils.extension.loadImages
import kotlinx.android.synthetic.main.item_spend.view.*

class SpendDetailAdapter (var spendDetail : ArrayList<SpendDetail>, private val mlistener: onClickItemSpend)
    : RecyclerView.Adapter<SpendDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_spend,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return spendDetail.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(spendDetail[position])
        holder.itemView.setOnClickListener {
            mlistener.onClickItemSpend(spendDetail[position])
        }
    }

    fun setData(list: ArrayList<SpendDetail>){
        this.spendDetail = list
        notifyDataSetChanged()
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(spendDetail: SpendDetail) {
            itemView.tvJob.text = spendDetail.title
            itemView.tvDate.text = spendDetail.date_transaction
            itemView.tvMoneySpend.text = spendDetail.amount.toString()
            itemView.tvSpend.text = (position + 1).toString()
        }
    }
    interface onClickItemSpend {
        fun onClickItemSpend(spend: SpendDetail)
    }
}