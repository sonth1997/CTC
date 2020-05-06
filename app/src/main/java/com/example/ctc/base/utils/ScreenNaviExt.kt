package com.example.ctc.base.utils

import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.ctc.R
import com.example.ctc.base.activity.activity.LeftHandle

fun FragmentActivity.goBack(@IdRes holder: Int = R.id.fragmentHolder): Boolean {
    return if (!hideKeyboard(this) && supportFragmentManager.backStackEntryCount > 0) {
        var isHandled = false
        val activeFragment = activePage(holder)
        if (activeFragment is LeftHandle) {
            isHandled = activeFragment.actionLeft()
        }
        if (isHandled)
            return true


        supportFragmentManager.popBackStack()
        val transaction = supportFragmentManager.beginTransaction()
        val currentFragment = supportFragmentManager.findFragmentById(holder)
        if (currentFragment != null) {
            transaction.remove(currentFragment)
            transaction.commit()
        }
        true
    } else false
}

fun Fragment.actionbar(): ActionBar? {
    return (activity as? AppCompatActivity)?.supportActionBar
}

    fun Fragment.goBack(): Boolean {
    return if (activity != null) {
        activity?.onBackPressed()
        true
    } else false
}

fun FragmentActivity.activePage(holder: Int = R.id.fragmentHolder): Fragment? {
    return supportFragmentManager.findFragmentById(holder)
}

fun FragmentActivity.isBackStackEmpty() = supportFragmentManager.backStackEntryCount == 0

fun Fragment.isBackStackEmpty() = fragmentManager?.backStackEntryCount == 0

fun Fragment.activePage(holder: Int = R.id.fragmentHolder): Fragment? {
    return fragmentManager?.findFragmentById(holder)
}

fun Fragment.activeChildPage(holder: Int = R.id.childFragmentHolder): Fragment? {
    return childFragmentManager.findFragmentById(holder)
}