package com.example.ctc.spend.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpendDetail (@SerializedName("transaction_id")
                   val transaction_id : Int?,
                   @SerializedName("title")
                   var title : String?,
                   @SerializedName("amount")
                        var amount : Int?,
                   @SerializedName("user_id")
                   val user_id : Int?,
                   @SerializedName("group_id")
                   val group_id : Int?,
                   @SerializedName("date_transaction")
                        var date_transaction : String?,
                   @SerializedName("note")
                        var note : String?,
                   @SerializedName("image")
                   var image : String?,
                   @SerializedName("status")
                   val status : Int?,
                   @SerializedName("created_at")
                   val created_at : String?,
                   @SerializedName("updated_at")
                   val updated_at : String?,
                   @SerializedName("deleted_at")
                   val deleted_at : String?
                   ): Parcelable
