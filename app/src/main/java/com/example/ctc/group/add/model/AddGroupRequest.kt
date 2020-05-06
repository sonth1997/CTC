package com.example.ctc.group.add.model

import com.google.gson.annotations.SerializedName

data class AddGroupRequest(@SerializedName("name")
                            var name : String = "",
                           @SerializedName ("image")
                           var image : String)
