package com.example.ctc

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.ctc.api.RetrofitApi
import com.example.ctc.base.Api

/**
 * @author HungHN on 3/15/2019.
 */
class MyApp : Application() {

    lateinit var api: Api
        private set

    private var appVisible = true

    override fun onCreate() {
        super.onCreate()
        myAppInstance = this
        api = RetrofitApi()
        val lifecycleObserver = AppLifecycleListener()
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleObserver)
    }

    val isAppVisible
        get() = appVisible

    companion object {

        private lateinit var myAppInstance: MyApp

        fun get(): MyApp {
            return myAppInstance
        }
    }

    inner class AppLifecycleListener : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onMoveToForeground() {
            appVisible = true
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onMoveToBackground() {
            appVisible = false
        }
    }
}
