package com.example.ctc.base.api.wrapper

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.wes.base.api.listener.ParseResponseListener

import java.lang.ref.SoftReference

/**
 * @author HungHN on 3/15/2019.
 * An implementation of [ParseResponseListener]
 *
 *
 * Use existed view to execute [ParseResponseListener.onResponse] on UI thread.
 */

class ParseUIResponseRS<T>(parseResponseListener: ParseResponseListener<T>) : ParseResponseListener<T> {

    private val rsReference: SoftReference<ParseResponseListener<T>> = SoftReference(parseResponseListener)
    private val sUIThreadHandler = Handler(Looper.getMainLooper())

    @Throws(Exception::class)
    override fun parse(requestId: String, response: String): T? {
        val listener = rsReference.get()

        return listener?.parse(requestId, response)

    }

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
