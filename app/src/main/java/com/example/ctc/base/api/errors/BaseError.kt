package com.example.ctc.base.api.errors

/**
 * @author HungHN on 3/15/2019.
 */
open class BaseError(open val requestId: String, open val response: String) : Exception()
