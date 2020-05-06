package com.example.ctc.spend.add.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
class Image: Parcelable{
    @SerializedName("objects")
    val objects : ArrayList<String> = arrayListOf()
}