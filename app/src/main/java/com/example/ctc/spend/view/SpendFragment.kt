package com.example.ctc.spend.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ctc.R
import com.example.ctc.api.RetrofitApi
import com.example.ctc.base.utils.ImageUtil
import com.example.ctc.base.utils.goBack
import com.example.ctc.group.model.Group
import com.example.ctc.group.update.UpdateGroupFragment
import com.example.ctc.spend.add.view.AddSpendFragment
import com.example.ctc.spend.detail.view.SpendDetailAfterFragment
import com.example.ctc.spend.model.SpendDetail
import com.example.ctc.utils.extension.loadImages
import kotlinx.android.synthetic.main.fragment_spend.*

class SpendFragment : Fragment(),
    SpendDetailAdapter.onClickItemSpend {

    val api = RetrofitApi()
    private var spendDetail: ArrayList<SpendDetail> = arrayListOf()
    private var group: Group? = null
    private val spend : SpendDetail ? = null
    private lateinit var adapter: SpendDetailAdapter


    companion object {
        const val KEY_ID = "key.type"
        const val REQUEST_ADDSPEND = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        group = arguments?.getParcelable(KEY_ID)
    }

    private fun spend() {
        api.spend("", group?.group_id.toString(), error = { _, err ->
            Log.e("log err", err.message.toString())
        }, success = { _, response ->
            if (response.isSuccess) {
                spendDetail = response.data ?: arrayListOf()
                adapter.setData(spendDetail)
            } else {
                Log.e("log err", response.message.toString())
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val text = group?.group_id
        outState.putString("a", text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_spend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SpendDetailAdapter(spendDetail, this)
        rcvSpend.layoutManager = LinearLayoutManager(context)
        rcvSpend.adapter = adapter
        iniViews()
        onClick()
        spend()
    }


    private fun iniViews() {
        arguments?.let { it ->
            if (!it.isEmpty)
                group = it.getParcelable(KEY_ID)
            group?.let {
                imgGroup.loadImages(it.image)
                tvGroup.text = it.name
            }
        }
    }
    private fun onClick() {
        imgBackSpend.setOnClickListener {
           goBack()
        }
        imgSettingSpend.setOnClickListener {
            val menuSpend = PopupMenu(context, it)
            menuSpend.menuInflater.inflate(R.menu.menu_actionbar, menuSpend.menu)
            menuSpend.show()
            menuSpend.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.groupSetting -> {
//                        launchSetNameFragment()
                        findNavController().navigate(
                            R.id.action_spendFragment2_to_updateGroupFragment
                            ,Bundle().apply {
                                putParcelable(UpdateGroupFragment.KEY_UPDATE, group)
                            })
                    }
                }
                false
            }
        }
        floatingButtonSpend.setOnClickListener {
            findNavController().navigate(R.id.action_spendFragment2_to_addSpendFragment , Bundle().apply {
                putParcelable(AddSpendFragment.KEY_ADD_SPEND, group)
            })
        }
    }

    override fun onClickItemSpend(spend: SpendDetail) {
        findNavController().navigate(R.id.action_spendFragment2_to_spendDetailAfterFragment,Bundle().apply {
            putParcelable(SpendDetailAfterFragment.KEY_SPEND_AFTER,spend)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) return
        when (requestCode) {
            REQUEST_ADDSPEND -> {
                data?.data?.let { uri ->
                    context?.let {
                        ImageUtil.loadImageSpend(it, uri, imageView = imgGroup)

                    }
                }
            }
        }
    }
    //    inner class MyFragment(manager: FragmentManager): FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
//        private val fragmentList: MutableList<Fragment> = ArrayList()
//        private val titleList: MutableList<String> = ArrayList()
//
//        override fun getItem(position: Int): Fragment {
//            return fragmentList[position]
//        }
//        override fun getCount(): Int {
//            return fragmentList.size
//        }
//            fragmentList.add(fragment)
//        fun addFragment(fragment: Fragment,title: String){
//            titleList.add(title)
//        }
//        override fun getPageTitle(position: Int): CharSequence? {
//            return titleList[position]
//        }
//    }
//    private fun tabLayoutSpend(){
//        adapter = MyFragment(childFragmentManager)
//        adapter.addFragment(SpendFragmentDetail.newInstance(spendDetail[0]),"TẤT CẢ")
//        adapter.addFragment(AFragment()," ĐÃ THANH TOÁN")
//        adapter.addFragment(BFragment(),"CHƯA THANH TOÁN")
//        viewPagerSpend.adapter = adapter
//        tabLayoutSpend.setupWithViewPager(viewPagerSpend)
//    }
}