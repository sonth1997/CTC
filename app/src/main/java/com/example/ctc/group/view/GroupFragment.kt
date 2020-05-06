package com.example.ctc.group.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ctc.R
import com.example.ctc.api.RetrofitApi
import com.example.ctc.group.model.Group
import com.example.ctc.spend.view.SpendFragment
import kotlinx.android.synthetic.main.fragment_group.*

class GroupFragment : Fragment(), GroupAdapter.onClickItemGroup {

        private lateinit var  adapter : GroupAdapter

        var api  = RetrofitApi()
        var group: ArrayList<Group> =  arrayListOf()

        companion object {
            private const val GROUP = "group.list"
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            group = arguments?.getParcelableArrayList(GROUP) ?: arrayListOf()
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_group,container,false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            onClick()
            adapter = GroupAdapter(group, this)
            rcvGroup.layoutManager = LinearLayoutManager(context)
            rcvGroup.adapter = adapter
            }
        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            group()
        }
        private fun group(){
                api.group("",error = { _, err ->
                    Log.e("log err",err.message.toString())
                },success = { _, response ->
                    if (response.isSuccess) {
                        group = response.data ?: arrayListOf()
//                        group.map {
//                            it.image = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/16/Facebook-icon-1.png/600px-Facebook-icon-1.png"
//                        }
                        adapter.setData(group)
                    } else {
                        Log.e("log err", response.message.toString())
                    }
                })
            }
            private fun onClick() {
                btnAddGroup.setOnClickListener {
                    findNavController().navigate(R.id.action_nav_group_to_addGroupFragment)
                }
            }
                override fun onClickItemMain(group: Group) {
                findNavController().navigate(R.id.action_nav_group_to_spendFragment2, Bundle().apply {
                    putParcelable(SpendFragment.KEY_ID, group)
                })}

}