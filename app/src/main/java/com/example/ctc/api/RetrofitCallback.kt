package com.example.ctc.api
import com.example.ctc.api.errors.ParserError
import com.example.ctc.api.errors.ServerError
import com.example.ctc.base.ApiError
import com.example.ctc.base.ApiSuccess
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RetrofitCallback<T>(private val requestId: String,
                          private val apiError: ApiError,
                          private val apiSuccess: ApiSuccess<T>) : Callback<T> {

    override fun onFailure(call: Call<T>?, t: Throwable?) {
        apiError.invoke(requestId, ServerError(requestId, t.toString()))
    }

    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        response?.body()?.let {
            apiSuccess.invoke(requestId, it)
            return
        }
        apiError.invoke(requestId, ParserError(requestId))
    }
}