package com.example.ctc.base.api.wrapper

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.ctc.base.api.listener.ApiResponseListener

import java.lang.ref.SoftReference

/**
 * @author HungHN on 3/15/2019.
 * An implementation of [ApiResponseListener]
 *
 *
 * Use existed view to execute [ApiResponseListener.onResponse] on UI thread.
 */

class ApiUIResponseRS<in T>(responseListener: ApiResponseListener<T>) : ApiResponseListener<T> {

    private val rsReference: SoftReference<ApiResponseListener<T>> = SoftReference(responseListener)
    private val sUIThreadHandler = Handler(Looper.getMainLooper())

    override fun onResponse(requestId: String, response: T) {
        val responseListener = rsReference.get()

        if (responseListener == null) {
            Log.d("ApiUIResponseRS", "Response Listener is null")
            return
        }

        sUIThreadHandler.post { responseListener.onResponse(requestId, response) }
    }

    override fun onError(requestId: String, e: Exception) {
        val responseListener = rsReference.get()

        if (responseListener == null) {
            Log.d("ApiUIResponseRS", "Response Listener is null")
            return
        }

        sUIThreadHandler.post { responseListener.onError(requestId, e) }

    }
}
