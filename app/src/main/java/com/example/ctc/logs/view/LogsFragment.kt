package com.example.ctc.logs.view

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ctc.R
import com.example.ctc.api.RetrofitApi
import com.example.ctc.logs.model.Logs
import kotlinx.android.synthetic.main.fragment_word.*

class LogsFragment : Fragment() {

    private lateinit var  adapter : LogsAdapter

    var api  = RetrofitApi()
    var logs: ArrayList<Logs> =  arrayListOf()
    private var log : Logs ? = null
    companion object {
        private const val LOGS = "logs.list"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logs = arguments?.getParcelableArrayList(LOGS) ?: arrayListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_word,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = LogsAdapter(logs)
        rcvLogs.layoutManager = LinearLayoutManager(context)
        rcvLogs.adapter = adapter
        logs()
        spand()
    }
   private fun spand(){
       val spannable = SpannableStringBuilder()
       spannable.setSpan(
           ForegroundColorSpan(Color.RED),
           8, // start
           12, // end
           Spannable.SPAN_EXCLUSIVE_INCLUSIVE
       )
   }

    private fun logs(){
        api.logs("",error = { _, err ->
            Log.e("log err",err.message.toString())
        },success = { _, response ->
            if (response.isSuccess) {
                logs = response.data ?: arrayListOf()
                Log.e("success", response.message.toString())
                adapter.setData(logs)
            } else {
                Log.e("loi code", response.message.toString())
            }
        })
    }

}