package com.wes.base.api.listener

/**
 * @author HungHN on 3/15/2019.
 */
interface ParseResponseListener<T> {

    @Throws(Exception::class)
    fun parse(requestId: String, response: String): T?

    fun onResponse(requestId: String, response: T)

    fun onError(requestId: String, e: Exception)
}
