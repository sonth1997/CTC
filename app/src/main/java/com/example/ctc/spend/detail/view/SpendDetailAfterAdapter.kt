package com.example.ctc.spend.detail.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ctc.R
import com.example.ctc.spend.detail.model.Split
import kotlinx.android.synthetic.main.item_note.view.*
import kotlinx.android.synthetic.main.item_receipt.view.*
import kotlinx.android.synthetic.main.item_split_detail.view.*

class SpendDetailAfterAdapter ( val TAG: String): RecyclerView.Adapter<SpendDetailAfterAdapter.ViewHolder>() {

    private var mData: MutableList<Split> = mutableListOf()
    fun setData(mData: MutableList<Split>) {
        this.mData = mData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when(TAG){
            SPLIT -> return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_split_detail, parent, false))
            NOTE -> return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false))
            RECEIPT -> return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_receipt, parent, false))
            else -> return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(mData[position], TAG)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(split: Split, TAG: String) {
            when(TAG){
                SPLIT -> {
                    itemView.imgSplit.setImageResource(split.imgSplitDetail)
                    itemView.tvSplit.text = split.tvSplitDetail
                }
                NOTE -> {
                    itemView.imgNote.setImageResource(split.imgNoteDetail)
                    itemView.tvNameNote.text = split.tvNameNote
                    itemView.tvComment.text = split.tvComment
                }
                RECEIPT -> {
                    itemView.imgReceipt.setImageResource(split.imgReceipt)
                }
            }
        }
    }

    companion object{
        val SPLIT = "SPLIT"
        val NOTE = "NOTE"
        val RECEIPT = "RECEIPT"
    }
}