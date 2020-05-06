package com.example.ctc.login.model

import com.google.gson.annotations.SerializedName

data class Name(@SerializedName ("access_token")
                var access_token : String?,
                @SerializedName ("token_type")
                var token_type : String?,
                @SerializedName ("expires_in")
                var expires_in : String?,
                @SerializedName ("user")
                var user : User?
                )
