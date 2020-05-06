package com.example.ctc.login.model

import com.google.gson.annotations.SerializedName

data class User(@SerializedName ("email")
                var email : String?,
                @SerializedName ("bod")
                var bod : String?,
                @SerializedName ("name")
                var name : String?,
                @SerializedName ("updated_at")
                var updated_at : String?,
                @SerializedName ("created_at")
                var created_at : String?,
                @SerializedName ("user_id")
                var user_id : String?
                )
