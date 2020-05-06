package com.example.ctc.extensions

import androidx.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ctc.MyApp

/**
 * @author HungHN on 3/15/2019.
 */

val AppContext = MyApp.get()

fun ViewGroup.inflate(@LayoutRes resource: Int): View = LayoutInflater.from(context).inflate(resource, this, false)