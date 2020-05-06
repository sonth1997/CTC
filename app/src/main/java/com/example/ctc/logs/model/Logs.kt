package com.example.ctc.logs.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Logs (@SerializedName("id")
                 var id : Int?,
                 @SerializedName("user_id")
                 var user_id : Int?,
                 @SerializedName("object_id")
                 var object_id : Int?,
                 @SerializedName("content")
                 var content : String?,
                 @SerializedName("revert_on")
                 var revert_on : String?,
                 @SerializedName("activity")
                 var activity : Int?,
                 @SerializedName("type")
                 var type : Int?,
                 @SerializedName("created_at")
                 var created_at : String?,
                 @SerializedName("updated_at")
                 var updated_at : String?,
                 @SerializedName("deleted_at")
                 var deleted_at : String?) : Parcelable
