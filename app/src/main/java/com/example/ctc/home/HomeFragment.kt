package com.example.ctc.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.fragment.findNavController
import com.example.ctc.R
import com.example.ctc.dialog.DialogHome
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_past.*
import java.util.ArrayList

class HomeFragment : Fragment(),DialogHome.OnInputSelected {

//
//    override fun sendInput(input: String?) {
//        (parentFragment as? HomeFragment)?.resetPage(input ?: "")
//    }

//    private lateinit var adapter: MyFragment
//    private lateinit var adapterTimeFragment: TimeFragment

    override fun sendInput(input: String?) {
        tvDateHome.text = input
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_past, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()
        val barEntries = ArrayList<BarEntry>()
        barEntries.add(BarEntry(24f, 0f))
        barEntries.add(BarEntry(15f, 1f))
        barEntries.add(BarEntry(34f, 2f))
        barEntries.add(BarEntry(44f, 3f))
        barEntries.add(BarEntry(40f, 4f))
        val barDataSet = BarDataSet(barEntries, "Số tiền")

        val theDate = arrayListOf<String>()
        theDate.add("aaa")
        theDate.add("aaa")
        theDate.add("aaa")
        theDate.add("aaa")
        theDate.add("aaa")

        val theData = BarData(barDataSet)
        barCharGroup.data = theData

        barCharGroup.setTouchEnabled(true)
        barCharGroup.isDragEnabled = true
        barCharGroup.setScaleEnabled(true)
    }

    private fun onClick() {
        tvDateHome.setOnClickListener {
            val dialog = DialogHome()
            dialog.setTargetFragment(this, 1)
            dialog.show(fragmentManager!!, "a")
        }
    }
}

//    inner class MyFragment(manager: FragmentManager): FragmentPagerAdapter(manager){
//        private val fragmentList: MutableList<Fragment> = ArrayList()
//        private val titleList: MutableList<String> = ArrayList()
//
//        override fun getItem(position: Int): Fragment {
//            return fragmentList[position]
//        }
//        override fun getCount(): Int {
//            return fragmentList.size
//        }
//        fun addFragment(fragment: Fragment,title: String){
//            fragmentList.add(fragment)
//            titleList.add(title)
//        }
//        override fun getPageTitle(position: Int): CharSequence? {
//            return titleList[position]
//        }
//    }
//    inner class TimeFragment(manager: FragmentManager): FragmentPagerAdapter(manager){
//        private val fragmentListT: MutableList<Fragment> = ArrayList()
//        private val titleListT: MutableList<String> = ArrayList()
//
//        override fun getItem(position: Int): Fragment {
//            return fragmentListT[position]
//        }
//        override fun getCount(): Int {
//            return fragmentListT.size
//        }
//        fun addFragment(fragment: Fragment,title: String){
//            fragmentListT.add(fragment)
//            titleListT.add(title)
//        }
//        override fun getPageTitle(position: Int): CharSequence? {
//            return titleListT[position]
//        }
//    }
//    private fun tabLayoutHome(){
//        adapter = MyFragment(childFragmentManager)
//        adapter.addFragment(PastFragment(),"THÁNG TRƯỚC - HIỆN TẠI")
//        viewPagerHome.adapter = adapter
//        tabLayoutHome.setupWithViewPager(viewPagerHome)
//    }
//    private fun tabLayoutPast(title: String){
//        adapterTimeFragment = TimeFragment(childFragmentManager)
//        adapterTimeFragment.addFragment(PastFragment(), title)
//        viewPagerHome.adapter = adapterTimeFragment
//        tabLayoutHome.setupWithViewPager(viewPagerHome)
//    }
//    fun resetPage(title: String){
//        tabLayoutPast(title)
//    }
