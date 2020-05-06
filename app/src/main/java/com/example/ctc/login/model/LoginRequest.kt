package com.example.ctc.login.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(@SerializedName ("bod")
                        var bod : String?,
                        @SerializedName("email")
                        var email : String? = null,
                        @SerializedName ("name")
                        var name : String?
                        ){
    @SerializedName ("avatar")
    var avatar : String? = null
}
