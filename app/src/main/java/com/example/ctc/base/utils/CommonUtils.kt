package com.example.ctc.base.utils

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.util.Size
import android.view.inputmethod.InputMethodManager
import java.text.Collator

fun showKeyboard(activity: Activity) {
    try {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(activity.currentFocus, InputMethodManager.SHOW_IMPLICIT)
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

fun hideKeyboard(activity: Activity): Boolean {
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    try {
        if (imm.isAcceptingText) {
            return imm.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return false
}

fun screenWidth(activity: Activity): Int {
    val metric = DisplayMetrics()
    activity.windowManager.defaultDisplay.getMetrics(metric)
    return metric.widthPixels
}