package com.example.ctc.api.errors

import com.example.ctc.base.api.errors.BaseError

/**
 * @author HungHN on 3/15/2019.
 */
data class ServerError(override val requestId: String,
                       override val response: String = "Server error") : BaseError(requestId, response)
