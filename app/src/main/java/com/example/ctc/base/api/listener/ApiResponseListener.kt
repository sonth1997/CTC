package com.example.ctc.base.api.listener

/**
 * @author HungHN on 3/15/2019.
 */

interface ApiResponseListener<in T> {

    fun onResponse(requestId: String, response: T)

    fun onError(requestId: String, e: Exception)

}
