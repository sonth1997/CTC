package com.example.ctc.group.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Group(@SerializedName ("group_id")
                 val group_id : Int?,
                 @SerializedName ("name")
                 var name : String?,
                 @SerializedName ("invite_url")
                 val invite_url : String?,
                 @SerializedName ("date_report")
                 val date_report : String?,
                 @SerializedName ("created_at")
                 val created_at : String?,
                 @SerializedName ("updated_at")
                 val updated_at : String?,
                 @SerializedName ("deleted_at")
                 val deleted_at : String?,
                 @SerializedName ("image")
                 var image: String ): Parcelable