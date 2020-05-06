package com.example.ctc.base
import com.google.gson.annotations.SerializedName

data class ApiResponse <T>(@SerializedName("code")
                           var code: Int,
                           @SerializedName("status")
                           var status: Boolean,
                           @SerializedName("message")
                           var message: String?,
                           @SerializedName("data")
                           var data: T?) {

    val isSuccess: Boolean
        get() = (code == 200)
}